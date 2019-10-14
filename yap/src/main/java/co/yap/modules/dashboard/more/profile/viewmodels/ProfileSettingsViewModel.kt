package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.View.GONE
import android.view.View.VISIBLE
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.states.ProfileStates
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager
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
    override var showExpiredBadge: Boolean = false
    override lateinit var data: GetMoreDocumentsResponse
    override val repository: CustomersRepository = CustomersRepository
    lateinit var multiPartImageFile: MultipartBody.Part

    override val state: ProfileStates =
        ProfileStates()

    override var clickEvent: SingleClickEvent = SingleClickEvent()


    override fun handlePressOnPersonalDetail(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnPrivacy(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnPasscode(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnAppNotification(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnTermsAndConditions(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnInstagram(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnTwitter(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnFaceBook(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnLogOut(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnAddNewPhoto(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnPhoto(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnBackButton() {
    }


    override fun onResume() {
        super.onResume()

        setToolBarTitle(getString(Strings.screen_profile_settings_display_text_title))


    }

    override fun onCreate() {
        super.onCreate()

        requestProfileDocumentsInformation()
        state.fullName = MyUserManager.user!!.currentCustomer.getFullName()
        if (MyUserManager.user!!.currentCustomer.getPicture().isNotEmpty()) {
            state.profilePictureUrl = MyUserManager.user!!.currentCustomer.getPicture()
        } else {
            state.fullName = MyUserManager.user!!.currentCustomer.getFullName()
            state.nameInitialsVisibility = GONE
        }
    }

    override fun uploadProfconvertUriToFile(selectedImageUri: Uri) {
        val file = File(selectedImageUri.path)
        val reqFile = RequestBody.create(MediaType.parse("image/"), file)
        multiPartImageFile =
            MultipartBody.Part.createFormData("profile-picture", file.name, reqFile)

        requestUploadProfilePicture()
    }

    override fun getRealPathFromUri(context: Context, uri: Uri): String {
        var path = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        val column_index: Int
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            path = cursor.getString(column_index)
            cursor.close()
        }
        return path
    }


    override fun requestUploadProfilePicture() {

        launch {
            state.loading = true

            when (val response = repository.uploadProfilePicture(multiPartImageFile)) {
                is RetroApiResponse.Success -> {

                    if (null != response.data.data) {
                        state.profilePictureUrl = response.data.data.imageURL
                        MyUserManager.user!!.currentCustomer.setPicture(response.data.data.imageURL)
                        state.fullName = MyUserManager.user!!.currentCustomer.getFullName()
                        state.nameInitialsVisibility = VISIBLE
                    }
                }

                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.fullName = MyUserManager.user!!.currentCustomer.getFullName()
                    state.nameInitialsVisibility = GONE

                }

            }

            state.loading = false
        }
    }

    override fun requestProfileDocumentsInformation() {

        launch {
            when (val response = repository.getMoreDocumentsByType("EMIRATES_ID")) {

                is RetroApiResponse.Success -> {
                    data = response.data

                    if (!data.data.dateExpiry.isNullOrEmpty()) {
                        getExpiryDate(data.data.dateExpiry)
                    }
                }

                is RetroApiResponse.Error -> {
                    state.errorBadgeVisibility = VISIBLE
                    showExpiredBadge = true
                }
            }
        }
    }

    fun getExpiryDate(expiryDateString: String) {

        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val expireyDate = simpleDateFormat.parse(expiryDateString)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)

        val prevDay = simpleDateFormat.format(cal.time)
        val previousDayDate = simpleDateFormat.parse(prevDay)

        if (expireyDate > previousDayDate) {
            state.errorBadgeVisibility = GONE
            showExpiredBadge = false
        } else {
            state.errorBadgeVisibility = VISIBLE
            showExpiredBadge = true
        }
    }
}