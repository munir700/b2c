package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.BuildConfig
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.isFutureDate
import co.yap.yapcore.helpers.DateUtils.nextYear
import co.yap.yapcore.helpers.extentions.dummyEID
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.getFormattedDate
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import com.digitify.identityscanner.core.arch.Gender
import com.digitify.identityscanner.docscanner.models.Identity
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class KYCHomeViewModel(application: Application) : KYCChildViewModel<IKYCHome.State>(application),
    IKYCHome.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository
        get() = CustomersRepository
    override val state: KYCHomeState = KYCHomeState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        requestDocuments()
        parentViewModel?.name?.value =
            getString(Strings.screen_b2c_kyc_home_display_text_sub_heading).format(parentViewModel?.name?.value)
        setProgress(20)
    }

    override fun handlePressOnNextButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnScanCard(id: Int) {
        if (state.eidScanStatus != DocScanStatus.DOCS_UPLOADED && state.eidScanStatus != DocScanStatus.SCAN_COMPLETED) {
            clickEvent.setValue(id)
        }
    }

    override fun handlePressOnSkipButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onEIDScanningComplete(result: IdentityScannerResult) {
        uploadDocuments(result)
    }

    private fun uploadDocuments(result: IdentityScannerResult) {
        if (!result.document.files.isNullOrEmpty() && result.document.files.size < 3) {

            val fileFront = File(result.document.files[0].croppedFile)
            val fileBack = File(result.document.files[1].croppedFile)
            parentViewModel?.paths?.clear()
            parentViewModel?.paths?.add(result.document.files[0].croppedFile)
            parentViewModel?.paths?.add(result.document.files[1].croppedFile)

            val fileFrontReqBody = fileFront.asRequestBody("image/*".toMediaTypeOrNull())
            val partFront =
                MultipartBody.Part.createFormData("files_f", fileFront.name, fileFrontReqBody)

            val fileBackReqBody = fileBack.asRequestBody("image/*".toMediaTypeOrNull())
            val partBack =
                MultipartBody.Part.createFormData("files_b", fileBack.name, fileBackReqBody)
            launch {
                state.loading = true
                when (val response = repository.detectCardData(partFront, partBack)) {

                    is RetroApiResponse.Success -> {
                        val data = response.data.data
                        if (data != null) {
                            val identity = Identity()
                            identity.nationality = data.nationality
                            identity.gender =
                                if (data.sex.equals("M", true)) Gender.Male else Gender.Female
                            identity.givenName = data.names
                            trackEventWithAttributes(
                                SessionManager.user,
                                eidExpireDate = getFormattedDate(data.expiration_date)
                            )
                            identity.expirationDate =
                                DateUtils.stringToDate(data.expiration_date, "yyMMdd")
                            val dob = DateUtils.stringToDate(data.date_of_birth, "yyMMdd")
                            identity.dateOfBirth = if (isFutureDate(dob) == true) nextYear(
                                dob,
                                -100
                            ) else DateUtils.stringToDate(data.date_of_birth, "yyMMdd")
                            identity.citizenNumber = data.optional1
                            identity.isoCountryCode2Digit = data.isoCountryCode2Digit
                            identity.isoCountryCode3Digit = data.isoCountryCode3Digit
                            result.identity = identity
                            parentViewModel?.identity = identity
                            state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
                        }
                    }

                    is RetroApiResponse.Error -> {
                        trackEvent(KYCEvents.EID_FAILURE.type)
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                        parentViewModel?.paths?.forEach { filePath ->
                            File(filePath).deleteRecursively()
                        }
                    }
                }

                state.loading = false
            }
        } else {
            state.toast = "${"There is some issue with captured images"}^${AlertType.DIALOG.name}"
        }
    }

    override fun requestDocuments() {
        launch {
            state.loading = true
            when (val response = repository.getDocuments()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) state.eidScanStatus = DocScanStatus.DOCS_UPLOADED
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun requestDocumentsInformation(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.getMoreDocumentsByType("EMIRATES_ID")) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.document =
                        response.data.data?.customerDocuments?.get(0)?.documentInformation
                    val data = response.data?.data
                    data?.let { data ->
                        parentViewModel?.state?.identityNo?.set(
                            parentViewModel?.document?.identityNo?.replace(
                                "-",
                                ""
                            )
                        )
                        parentViewModel?.state?.firstName?.set(data.firstName)
                        parentViewModel?.state?.lastName?.set(data.lastName)
                        parentViewModel?.state?.nationality?.set(data.nationality)
                        success.invoke()
                    }
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    if (response.error.statusCode == 400 || response.error.actualCode == "1073")
                        state.loading = false
                }
            }
        }
    }

    override fun isFromAmendment() = parentViewModel?.amendmentMap?.isNullOrEmpty() == false
}