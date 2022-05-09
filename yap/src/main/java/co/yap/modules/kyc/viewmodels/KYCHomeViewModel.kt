package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

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

    override fun requestDocuments() {
        launch {
            state.loading = true
            when (val response = repository.getDocuments()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.filter { it.documentType == "EMIRATES_ID" }?.let {
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

    override fun navigateTo(fromAmendment: Boolean) =
        if (fromAmendment) R.id.action_KYCHomeFragment_to_eidInfoReviewAmendmentFragment else R.id.action_KYCHomeFragment_to_eidInfoReviewFragment
}