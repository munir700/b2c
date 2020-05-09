package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import android.os.Build
import android.view.View.GONE
import android.view.View.VISIBLE
import co.yap.modules.dashboard.more.main.viewmodels.MoreBaseViewModel
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.states.ProfileStates
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.sizeInMb
import co.yap.yapcore.managers.MyUserManager
import com.bumptech.glide.Glide
import id.zelory.compressor.Compressor
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileSettingsViewModel(application: Application) :
    MoreBaseViewModel<IProfile.State>(application), IProfile.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var PROFILE_PICTURE_UPLOADED: Int = 100
    override var EVENT_LOGOUT_SUCCESS: Int = 101
    override val authRepository: AuthRepository = AuthRepository
    override val repository: CustomersRepository = CustomersRepository
    private val sharedPreferenceManager = SharedPreferenceManager(application)

    override val state: ProfileStates =
        ProfileStates()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnViewClick(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        toggleAchievementsBadgeVisibility(parentViewModel!!.BadgeVisibility)
        setToolBarTitle(getString(Strings.common_button_settings))
    }

    override fun onCreate() {
        super.onCreate()
        requestProfileDocumentsInformation()
        MyUserManager.user?.let {
            state.fullName = it.currentCustomer.getFullName()
            if (it.currentCustomer.getPicture() != null) {
                state.profilePictureUrl = it.currentCustomer.getPicture()!!
            } else {
                state.fullName = it.currentCustomer.getFullName()
                state.nameInitialsVisibility = GONE
            }
        }
    }

    override fun logout() {
        val deviceId: String? =
            sharedPreferenceManager.getValueString(KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response = authRepository.logout(deviceId.toString())) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(EVENT_LOGOUT_SUCCESS)
                    state.loading = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun requestUploadProfilePicture(actualFile: File) {
        launch {
            var file = actualFile
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                file = Compressor.compress(context, actualFile)
            }
            if (file.sizeInMb() < 25) {
                val reqFile =
                    RequestBody.create(MediaType.parse("image/${file.extension}"), file)
                val multiPartImageFile: MultipartBody.Part =
                    MultipartBody.Part.createFormData("profile-picture", file.name, reqFile)
                when (val response = repository.uploadProfilePicture(multiPartImageFile)) {
                    is RetroApiResponse.Success -> {

                        if (null != response.data.data) {
                            response.data.data?.let {
                                it.imageURL?.let { url -> state.profilePictureUrl = url }
                                MyUserManager.user?.currentCustomer?.setPicture(it.imageURL)
                                Glide.with(context)
                                    .load(it.imageURL).preload()
                                state.fullName =
                                    MyUserManager.user?.currentCustomer?.getFullName() ?: ""
                                state.nameInitialsVisibility = VISIBLE
                                state.loading = false
                            }
                        }
                    }

                    is RetroApiResponse.Error -> {
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                        state.fullName =
                            MyUserManager.user?.currentCustomer?.getFullName() ?: ""
                        state.nameInitialsVisibility = GONE
                        state.loading = false
                    }
                }
            } else {
                state.toast = "File size not supported^${AlertType.DIALOG.name}"
                state.loading = true
            }
        }
    }

    override fun requestProfileDocumentsInformation() {
        launch {
            state.loading = true
            when (val response = repository.getMoreDocumentsByType("EMIRATES_ID")) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.document =
                        response.data.data?.customerDocuments?.get(0)?.documentInformation

                    val data = response.data
                    data.data?.dateExpiry?.let {
                        getExpiryDate(it)
                    }
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    if (response.error.statusCode == 400 || response.error.actualCode == "1073")
                        state.isShowErrorIcon.set(true)
                    MyUserManager.eidStatus =
                        EIDStatus.NOT_SET  //set the document is required if not found
                    state.loading = false
                }
            }
        }
    }

    private fun getExpiryDate(expiryDateString: String) {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        simpleDateFormat.timeZone = TimeZone.getDefault()
        val expireyDate = simpleDateFormat.parse(expiryDateString)
        val cal = Calendar.getInstance()
        val currentDay = simpleDateFormat.format(cal.time)
        val currentDayDate = simpleDateFormat.parse(currentDay)
        state.isShowErrorIcon.set(expireyDate < currentDayDate)
        MyUserManager.eidStatus =
            if (expireyDate < currentDayDate) EIDStatus.EXPIRED else EIDStatus.VALID
    }
}