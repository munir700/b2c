package co.yap.modules.dashboard.more.profile.fragments

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.app.YAPApplication
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.viewmodels.ProfileSettingsViewModel
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.PermissionHelper
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.managers.MyUserManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_lite_dashboard.swTouchId
import kotlinx.android.synthetic.main.layout_profile_settings.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class ProfileSettingsFragment : MoreBaseFragment<IProfile.ViewModel>(), IProfile.View,
    CardClickListener {

    private lateinit var updatePhotoBottomSheet: UpdatePhotoBottomSheet
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_profile

    private val FINAL_TAKE_PHOTO = 1
    private val FINAL_CHOOSE_PHOTO = 2
    internal var permissionHelper: PermissionHelper? = null

    override val viewModel: IProfile.ViewModel
        get() = ViewModelProviders.of(this).get(ProfileSettingsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is MoreActivity)
            (context as MoreActivity).visibleToolbar()

        Glide.with(activity!!)

        var sharedPreferenceManager: SharedPreferenceManager =
            SharedPreferenceManager(requireContext())

        if (BiometricUtil.isFingerprintSupported
            && BiometricUtil.isHardwareSupported(requireContext())
            && BiometricUtil.isPermissionGranted(requireContext())
            && BiometricUtil.isFingerprintAvailable(requireContext())
        ) {
            val isTouchIdEnabled: Boolean =
                sharedPreferenceManager.getValueBoolien(
                    SharedPreferenceManager.KEY_TOUCH_ID_ENABLED,
                    false
                )
            swTouchId.isChecked = isTouchIdEnabled
            llSignInWithTouch.visibility = View.VISIBLE

            swTouchId.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                        true
                    )
                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, true)
                } else {
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_TOUCH_ID_ENABLED,
                        false
                    )
                }
            }
        } else {
            llSignInWithTouch.visibility = View.INVISIBLE
        }
    }

    override fun onClick(eventType: Int) {

        updatePhotoBottomSheet.dismiss()

        when (eventType) {
            Constants.EVENT_ADD_PHOTO -> {
                checkPermission(FINAL_TAKE_PHOTO)
            }

            Constants.EVENT_CHOOSE_PHOTO -> {
                checkPermission(FINAL_CHOOSE_PHOTO)
            }
        }
    }

    private fun checkPermission(type: Int) {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )

        permissionHelper!!.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                if (type == FINAL_TAKE_PHOTO) {
                    EasyImage.openCamera(this@ProfileSettingsFragment, FINAL_TAKE_PHOTO)
                } else {
                    EasyImage.openGallery(this@ProfileSettingsFragment, FINAL_CHOOSE_PHOTO)
                }

            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                if (type == FINAL_TAKE_PHOTO) {
                    if (grantedPermission.contains(Manifest.permission.CAMERA))
                        EasyImage.openCamera(this@ProfileSettingsFragment, FINAL_TAKE_PHOTO)
                } else {
                    if (grantedPermission.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        EasyImage.openGallery(this@ProfileSettingsFragment, FINAL_CHOOSE_PHOTO)
                }
            }

            override fun onPermissionDenied() {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDeniedBySystem() {
                permissionHelper!!.openAppDetailsActivity()


            }
        })
    }


    private fun onPhotosReturned(path: File, position: Int, source: EasyImage.ImageSource?) {
        viewModel.uploadProfconvertUriToFile(path.toUri())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, activity,
            object : DefaultCallback() {
                override fun onImagePicked(
                    imageFile: File?,
                    source: EasyImage.ImageSource?,
                    type: Int
                ) {
                    onPhotosReturned(imageFile!!, type, source)
                }

                override fun onImagePickerError(
                    e: Exception?,
                    source: EasyImage.ImageSource?,
                    type: Int
                ) {
                    //Some error handling
                    showToast(e!!.message.toString())
                }
            })
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun logoutAlert() {
        AlertDialog.Builder(this.activity!!)
            .setTitle(getString(R.string.screen_profile_settings_logout_display_text_alert_title))
            .setMessage(getString(R.string.screen_profile_settings_logout_display_text_alert_message))
            .setPositiveButton(getString(R.string.screen_profile_settings_logout_display_text_alert_logout),
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.logout()
                })

            .setNegativeButton(
                getString(R.string.screen_profile_settings_logout_display_text_alert_cancel),
                null
            )
            .show()
    }

    private fun doLogout() {
        AuthUtils.navigateToHardLogin(requireContext())
        MyUserManager.user = null
        MyUserManager.cardBalance.value = CardBalance()
        MyUserManager.cards = MutableLiveData()
        MyUserManager.cards.value?.clear()
        MyUserManager.userAddress = null
        MoreActivity.showExpiredIcon = false
        YAPApplication.clearFilters()
        activity?.finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionHelper != null) {
            permissionHelper!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvPersonalDetailView -> {

                    val action =
                        ProfileSettingsFragmentDirections.actionProfileSettingsFragmentToPersonalDetailsFragment(
                            viewModel.showExpiredBadge
                        )
                    findNavController().navigate(action)
                }

                R.id.tvPrivacyView -> {
                    Utils.showComingSoon(requireContext())
                }

                R.id.tvNotificationsView ->{
                    Utils.showComingSoon(requireContext())
                }

                R.id.tvChangePasscode -> {
                    findNavController().navigate(R.id.action_profileSettingsFragment_to_change_pascode_navigation)
                }

                R.id.tvTermsAndConditionView -> {
                    Utils.openWebPage(
                        co.yap.yapcore.constants.Constants.URL_TERMS_CONDITION,
                        "",
                        activity
                    )
                }

                R.id.tvFollowOnInstagram -> {
                    Utils.openInstagram(requireContext())
                }

                R.id.tvFollowOnTwitter -> {
                    Utils.openTwitter(requireContext())
                }

                R.id.tvLikeUsOnFaceBook -> {
                    startActivity(Utils.getOpenFacebookIntent(requireContext()))
                }

                R.id.ivProfilePic -> {
                    // change profile picture
                }

                R.id.tvLogOut -> {


                    logoutAlert()
                }

                R.id.rlAddNewProfilePic -> {
                    updatePhotoBottomSheet = UpdatePhotoBottomSheet(this)
                    updatePhotoBottomSheet.show(this!!.fragmentManager!!, "")
                }

                viewModel.PROFILE_PICTURE_UPLOADED -> {

                }
                viewModel.EVENT_LOGOUT_SUCCESS -> {
                    doLogout()
                }
            }
        })

    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

}