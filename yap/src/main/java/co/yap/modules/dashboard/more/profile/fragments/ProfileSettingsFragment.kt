package co.yap.modules.dashboard.more.profile.fragments

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.more.changepasscode.activities.ChangePasscodeActivity
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.viewmodels.ProfileSettingsViewModel
import co.yap.modules.others.helper.Constants
import co.yap.modules.webview.WebViewFragment
import co.yap.yapcore.constants.Constants.CAMERA_PERMISSION_REQUEST
import co.yap.yapcore.constants.Constants.KEY_IS_FINGERPRINT_PERMISSION_SHOWN
import co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.hasBitmap
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.managers.SessionManager
import kotlinx.android.synthetic.main.layout_profile_picture.*
import kotlinx.android.synthetic.main.layout_profile_settings.*
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class ProfileSettingsFragment : MoreBaseFragment<IProfile.ViewModel>(), IProfile.View,
    CardClickListener, EasyPermissions.PermissionCallbacks {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_profile
    internal var permissionHelper: PermissionHelper? = null
    lateinit var easyImage: EasyImage
    private lateinit var updatePhotoBottomSheet: UpdatePhotoBottomSheet
    private val takePhoto = 1
    private val pickPhoto = 2
    override val viewModel: IProfile.ViewModel
        get() = ViewModelProviders.of(this).get(ProfileSettingsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is MoreActivity) {
            (context as MoreActivity).visibleToolbar()
        }
        viewModel.state.buildVersionDetail = versionName
        val sharedPreferenceManager =
            SharedPreferenceManager(requireContext())

        if (BiometricUtil.hasBioMetricFeature(requireContext())) {
            val isTouchIdEnabled: Boolean =
                sharedPreferenceManager.getValueBoolien(
                    KEY_TOUCH_ID_ENABLED,
                    false
                )
            swTouchId.isChecked = isTouchIdEnabled
            llSignInWithTouch.visibility = View.VISIBLE

            swTouchId.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sharedPreferenceManager.save(
                        KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                        true
                    )
                    sharedPreferenceManager.save(KEY_TOUCH_ID_ENABLED, true)
                } else {
                    sharedPreferenceManager.save(
                        KEY_TOUCH_ID_ENABLED,
                        false
                    )
                }
            }
        } else {
            llSignInWithTouch.visibility = View.GONE
        }
    }

    override fun onClick(eventType: Int) {
        updatePhotoBottomSheet.dismiss()

        when (eventType) {

            Constants.EVENT_ADD_PHOTO -> {
                initEasyImage(takePhoto)
            }

            Constants.EVENT_CHOOSE_PHOTO -> {
                initEasyImage(pickPhoto)
            }

            Constants.EVENT_REMOVE_PHOTO -> {
                viewModel.requestRemoveProfilePicture {
                    if (it) ivProfilePic.setImageDrawable(null)
                }

            }

        }
    }

    private fun initEasyImage(type: Int) {
        if (hasCameraPermission()) {
            easyImage = EasyImage.Builder(requireContext())
                .setChooserTitle("Pick Image")
                .setFolderName("YAPImage")
                .allowMultiple(false)
                .build()
            when (type) {
                1 -> {
                    easyImage.openCameraForImage(this)

                }
                2 -> {
                    easyImage.openGallery(this)
                }
            }
            //  easyImage.openChooser(this)
        } else {
            EasyPermissions.requestPermissions(
                this, "This app needs access to your camera so you can take pictures.",
                CAMERA_PERMISSION_REQUEST, Manifest.permission.CAMERA
            )
        }

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
        SessionManager.doLogout(requireContext())
        activity?.finish()
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
                        ProfileSettingsFragmentDirections.actionProfileSettingsFragmentToPersonalDetailsFragment()
                    findNavController().navigate(action)
                }

                R.id.tvPrivacyView -> {
                    Utils.showComingSoon(requireContext())
                }

                R.id.tvNotificationsView -> {
                    Utils.showComingSoon(requireContext())
                }

                R.id.tvChangePasscode -> {
                    launchActivity<ChangePasscodeActivity>(type = FeatureSet.CHANGE_PASSCODE)
                }
                R.id.tvTermsAndConditionView -> {
                    startFragment(
                        fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                            co.yap.yapcore.constants.Constants.PAGE_URL to co.yap.yapcore.constants.Constants.URL_TERMS_CONDITION
                        ), showToolBar = false
                    )
                }

                R.id.tvFeesAndPricingPlansView -> {
                    startFragment(
                        fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                            co.yap.yapcore.constants.Constants.PAGE_URL to co.yap.yapcore.constants.Constants.URL_FEES_AND_PRICING_PLAN
                        ), showToolBar = false
                    )
                }
                R.id.tvFollowOnInstagram -> {
                    Utils.openInstagram(requireContext())
                }

                R.id.tvFollowOnTwitter -> {
                    Utils.openTwitter(requireContext())
                }

                R.id.tvLikeUsOnFaceBook -> {
                    Utils.getOpenFacebookIntent(requireContext())
                        ?.let { startActivity(it) }
                }

                R.id.ivProfilePic -> {
                }

                R.id.tvLogOut -> {
                    logoutAlert()
                }

                R.id.rlAddNewProfilePic -> {
                    this.fragmentManager?.let {

                        updatePhotoBottomSheet = UpdatePhotoBottomSheet(this, showRemovePhoto())

                        updatePhotoBottomSheet.show(it, "")

                    }
                }

                viewModel.PROFILE_PICTURE_UPLOADED -> {
                }

                viewModel.EVENT_LOGOUT_SUCCESS -> {
                    doLogout()
                }
            }
        })
    }

    private fun showRemovePhoto(): Boolean {

        return viewModel.state.profilePictureUrl.isNotEmpty() && ivProfilePic.hasBitmap()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            handleImagePickerResult(requestCode, resultCode, data)
        }
    }

    private fun handleImagePickerResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(
                    imageFiles: Array<MediaFile>,
                    source: MediaSource
                ) {
                    onPhotosReturned(imageFiles, source)
                }

                override fun onImagePickerError(
                    @NonNull error: Throwable,
                    @NonNull source: MediaSource
                ) {
                    viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                }

                override fun onCanceled(@NonNull source: MediaSource) {
                    viewModel.state.toast = "No image detected^${AlertType.DIALOG.name}"
                }
            })
    }

    private fun onPhotosReturned(path: Array<MediaFile>, source: MediaSource) {
        path.firstOrNull()?.let { mediaFile ->
            val ext = mediaFile.file.extension
            if (!ext.isBlank()) {
                when (ext) {
                    "png", "jpg", "jpeg" -> {
                        viewModel.clickEvent.call()
                        viewModel.requestUploadProfilePicture(mediaFile.file)
                        viewModel.state.imageUri = mediaFile.file.toUri()
                        ivProfilePic.setImageURI(mediaFile.file.toUri())
                    }
                    else -> {
                        viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                    }

                }
            } else {
                viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.CAMERA)
    }
}