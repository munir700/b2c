package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View.GONE
import android.view.View.VISIBLE
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.main.activities.MoreActivity.Companion.isDocumentRequired
import co.yap.modules.dashboard.more.main.viewmodels.MoreBaseViewModel
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.states.ProfileStates
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.MyUserManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
    override var showExpiredBadge: Boolean = false
    override lateinit var data: GetMoreDocumentsResponse
    override val authRepository: AuthRepository = AuthRepository
    override val repository: CustomersRepository = CustomersRepository
    lateinit var multiPartImageFile: MultipartBody.Part
    private val sharedPreferenceManager = SharedPreferenceManager(application)

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
        // setToolBarTitle(getString(Strings.screen_profile_settings_display_text_title))
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

    override fun logout() {
        val deviceId: String? =
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response = authRepository.logout(deviceId.toString())) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(EVENT_LOGOUT_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            // Set a little delay in case of no in
            // TODO: Fix this delay issue. It should not be written with a delay
            //Handler(Looper.getMainLooper()).postDelayed({ state.loading = false }, 500)

        }
    }

    override fun requestUploadProfilePicture() {

        launch {
            state.loading = true

            when (val response = repository.uploadProfilePicture(multiPartImageFile)) {
                is RetroApiResponse.Success -> {

                    if (null != response.data.data) {
                        response.data.data?.let {
                            it.imageURL?.let { state.profilePictureUrl = it }
                            MyUserManager.user!!.currentCustomer.setPicture(it.imageURL)
                            Glide.with(context)
                                .load(it.imageURL).listener(object : RequestListener<Drawable>
                                {
                                    override fun onLoadFailed(
                                        e: GlideException?,
                                        model: Any?,
                                        target: Target<Drawable>?,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        state.loading = false
                                        return false
                                    }

                                    override fun onResourceReady(
                                        resource: Drawable?,
                                        model: Any?,
                                        target: Target<Drawable>?,
                                        dataSource: DataSource?,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        state.loading = false
                                        return false
                                    }
                                }).preload()

                            state.fullName = MyUserManager.user!!.currentCustomer.getFullName()
                            state.nameInitialsVisibility = VISIBLE
                        }
                    }
                }

                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.fullName = MyUserManager.user!!.currentCustomer.getFullName()
                    state.nameInitialsVisibility = GONE
                    state.loading = false

                }

            }


        }
    }

    override fun requestProfileDocumentsInformation() {

        launch {
            when (val response = repository.getMoreDocumentsByType("EMIRATES_ID")) {

                is RetroApiResponse.Success -> {
                    data = response.data

                    if (!data.data.dateExpiry.isNullOrEmpty()) {
                        getExpiryDate(data.data.dateExpiry!!)
                    }
                }

                is RetroApiResponse.Error -> {
                    state.errorBadgeVisibility = VISIBLE
                    MoreActivity.showExpiredIcon = true
                    showExpiredBadge = true
                    if (response.error.message.equals("HomeTransactionListData not found")) {
                        isDocumentRequired = true
                    }
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
            MoreActivity.showExpiredIcon = false

        } else {
            state.errorBadgeVisibility = VISIBLE
            showExpiredBadge = true
            MoreActivity.showExpiredIcon = true
        }

    }
}