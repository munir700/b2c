package co.yap.modules.dashboard.more.profile.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.constants.Constants
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.viewmodels.ProfileSettingsViewModel
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.PermissionHelper
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_lite_dashboard.swTouchId
import kotlinx.android.synthetic.main.layout_profile_settings.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ProfileSettingsFragment : MoreBaseFragment<IProfile.ViewModel>(), IProfile.View,
    CardClickListener {
    val IMAGE_PATH = Environment
        .getExternalStorageDirectory().path + "/eidScan"

    private lateinit var updatePhotoBottomSheet: UpdatePhotoBottomSheet

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    private var imageUri: Uri? = null
    private val FINAL_TAKE_PHOTO = 1
    private val FINAL_CHOOSE_PHOTO = 2
    internal var pictureSelectionType = false
    internal var permissionHelper: PermissionHelper? = null


    var checkPermissionGranted = false

    override val viewModel: IProfile.ViewModel
        get() = ViewModelProviders.of(this).get(ProfileSettingsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is MoreActivity)
            (context as MoreActivity).visibleToolbar()

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
                takePicture()
            }

            Constants.EVENT_CHOOSE_PHOTO -> {
                // choose photo
                selectProfilePicture()

            }

            R.id.tvNotificationsView -> {
                showToast("start notifications")
            }
        }
    }

    fun checkPermission() {
        permissionHelper = PermissionHelper(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            100
        )

        permissionHelper!!.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                Log.d("ProfileSettingsFragment", "onPermissionGranted() called")
                pictureSelectionType

                if (pictureSelectionType) {
                    takePicture()

                } else {
                    selectProfilePicture()
                }

            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                Log.d(
                    "ProfileSettingsFragment",
                    "onIndividualPermissionGranted() called with: grantedPermission = [" + TextUtils.join(
                        ",",
                        grantedPermission
                    ) + "]"
                )
            }

            override fun onPermissionDenied() {
                Log.d("ProfileSettingsFragment", "onPermissionDenied() called")
            }

            override fun onPermissionDeniedBySystem() {
                Log.d("ProfileSettingsFragment", "onPermissionDeniedBySystem() called")
                permissionHelper!!.openAppDetailsActivity()


            }
        })
    }

    private fun selectProfilePicture() {
        pictureSelectionType = true
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) run {
            checkPermission()
        } else {
            pictureSelectionType = false

            val intent = Intent()
            intent.type = "image/jpeg"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Picture"
                ), FINAL_CHOOSE_PHOTO
            )
        }
    }


    private fun takePicture() {
        pictureSelectionType = true
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) run {
            checkPermission()
        } else {
            pictureSelectionType = true

            val tempFolder = File(IMAGE_PATH)

            for (f in tempFolder.listFiles())
                f.delete()
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val file = File(
                IMAGE_PATH, "IMG_" + timeStamp +
                        ".jpg"
            )

            imageUri = if (Build.VERSION.SDK_INT >= 24) {
                FileProvider.getUriForFile(
                    activity!!.applicationContext,
                    "co.yap.fileprovider",
                    file
                )
            } else {
                Uri.fromFile(file)
            }

            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, FINAL_TAKE_PHOTO)
        }
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: Exception) {
            Log.e("", "getRealPathFromURI Exception : $e")
            return ""
        } finally {
            cursor?.close()
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "title", null)
        return Uri.parse(path)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap? = null

        when (requestCode) {
            FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {

                    try {
                        imageUri = data!!.getData()
                        var selectedImage: Bitmap
                        if (imageUri == null) {
                            selectedImage = data!!.getExtras()!!.get("data") as Bitmap
                            selectedImage = data!!.data as Bitmap
                        } else {
                            selectedImage = getBitmap(imageUri)
                        }

                        val uri = getImageUri(activity!!, selectedImage)
                        val imgPath = getRealPathFromURI(activity!!, uri)
                        viewModel.uploadProfconvertUriToFile(Uri.parse(imgPath))

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = getBitmap(imageUri)

                    imageUri = getUri(bitmap)
                    if (imageUri != null) {

                        imageUri = Uri.parse(
                            viewModel.getRealPathFromUri(
                                this!!.context!!, imageUri!!
                            )
                        )

                        viewModel.uploadProfconvertUriToFile(imageUri!!)
                    }
                }
        }
    }

    fun getUri(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes)

        val path = MediaStore.Images.Media.insertImage(
            context!!.contentResolver,
            bitmap,
            Date().time.toString() + "_Title",
            null
        )
        return Uri.parse(path)
    }

    fun getBitmap(selectedimg: Uri?): Bitmap {
        val options = BitmapFactory.Options()
        options.inSampleSize = 3
        var fileDescriptor: AssetFileDescriptor? = null
        fileDescriptor = activity!!.getContentResolver().openAssetFileDescriptor(selectedimg!!, "r")
        return BitmapFactory.decodeFileDescriptor(
            fileDescriptor!!.fileDescriptor, null, options
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

    fun logoutAlert() {
        AlertDialog.Builder(this!!.activity!!)
            .setTitle(getString(R.string.screen_profile_settings_logout_display_text_alert_title))
            .setMessage(getString(R.string.screen_profile_settings_logout_display_text_alert_message))
            .setPositiveButton(getString(R.string.screen_profile_settings_logout_display_text_alert_logout),
                DialogInterface.OnClickListener { dialog, which ->
                    doLogout()
                })

            .setNegativeButton(
                getString(R.string.screen_profile_settings_logout_display_text_alert_cancel),
                null
            )
            .show()
    }

    private fun doLogout() {
        AuthUtils.navigateToHardLogin(requireContext())
        MyUserManager.cardBalance.value = CardBalance()
        MyUserManager.cards.value?.clear()
        MyUserManager.userAddress = null
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

                }

                R.id.tvChangePasscode -> {

                    findNavController().navigate(R.id.action_profileSettingsFragment_to_change_pascode_navigation)

                }

                R.id.tvTermsAndConditionView -> {

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
            }
        })

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }


}