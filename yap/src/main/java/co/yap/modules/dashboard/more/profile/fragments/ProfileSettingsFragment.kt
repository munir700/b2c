package co.yap.modules.dashboard.more.profile.fragments

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.constants.Constants
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.viewmodels.ProfileSettingsViewModel
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.layout_profile_picture.*
import java.io.File

class ProfileSettingsFragment : MoreBaseFragment<IProfile.ViewModel>(), IProfile.View,
    CardClickListener {

    private lateinit var updatePhotoBottomSheet: UpdatePhotoBottomSheet

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    private var imageUri: Uri? = null


    private val FINAL_TAKE_PHOTO = 1
    private val FINAL_CHOOSE_PHOTO = 2

    override val viewModel: IProfile.ViewModel
        get() = ViewModelProviders.of(this).get(ProfileSettingsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvPersonalDetailView -> {
                    findNavController().navigate(R.id.action_profileSettingsFragment_to_personalDetailsFragment)
                }

                R.id.tvPrivacyView -> {

                }

                R.id.tvChangePasscode -> {

                }

                R.id.tvTermsAndConditionView -> {

                }

                R.id.tvFollowOnInstagram -> {

                }

                R.id.tvFollowOnTwitter -> {

                }

                R.id.tvLikeUsOnFaceBook -> {

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
            }
        })
    }

    override fun onClick(eventType: Int) {

        updatePhotoBottomSheet.dismiss()

        when (eventType) {
            Constants.EVENT_ADD_PHOTO -> {
//                takePicture()
                val checkSelfPermission = ContextCompat.checkSelfPermission(
                    activity!!,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                val checkSelfCameraPermission = ContextCompat.checkSelfPermission(
                    activity!!,
                    android.Manifest.permission.CAMERA
                )
                if (checkSelfPermission != PackageManager.PERMISSION_GRANTED && checkSelfCameraPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                        ),
                        FINAL_TAKE_PHOTO
                    )
                } else {
                    takePicture()
                }
                //
            }

            Constants.EVENT_CHOOSE_PHOTO -> {
                // choose photo
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    FINAL_CHOOSE_PHOTO
                )

            }
        }
    }

    private fun takePicture() {
        val outputImage = File(activity!!.applicationContext.externalCacheDir, "output_image.jpg")
        if (outputImage.exists()) {
            outputImage.delete()
        }
        outputImage.createNewFile()
        imageUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                activity!!.applicationContext,
                "co.yap.fileprovider",
                outputImage
            )
        } else {
            Uri.fromFile(outputImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, FINAL_TAKE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {

                    showToast(data.toString())
                    val bitmap = BitmapFactory.decodeStream(
                        activity!!.getContentResolver().openInputStream(imageUri)
                    )
//                    picture!!.setImageBitmap(bitmap)


                }

            FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {

                    val bitmap = BitmapFactory.decodeStream(
                        activity!!.getContentResolver().openInputStream(imageUri)
                    )
                    ivProfilePic!!.setImageBitmap(bitmap)
//                    ivProfilePic!!.setImageURI(imageUri)

                }
        }
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
        activity?.finish()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 ->
                if (grantResults.isNotEmpty() && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                    takePicture()
                } else {

                }
        }
    }

}