package co.yap.yapcore

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.annotation.NonNull
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.constants.RequestCodes.REQUEST_CAMERA_PERMISSION
import co.yap.yapcore.enums.ImageTypes
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseBindingImageFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {

    private lateinit var easyImage: EasyImage


    @AfterPermissionGranted(REQUEST_CAMERA_PERMISSION)
    fun openImagePicker(imageTypes: ImageTypes) {
        if (hasCameraPermission()) {
            easyImage =
                EasyImage.Builder(requireContext()) // Chooser only
                    .setChooserTitle("Pick Image")
                    .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                    .setFolderName("YAPImage")
                    .allowMultiple(false)
                    .build()
            when (imageTypes) {
                ImageTypes.OPEN_CHOOSER ->
                    easyImage.openChooser(this)
                ImageTypes.OPEN_CAMERA ->
                    easyImage.openChooser(this)
                ImageTypes.OPEN_GALLERY ->
                    easyImage.openChooser(this)
            }
        } else {
            EasyPermissions.requestPermissions(
                this, Translator.getString(requireContext(), Strings.rationale_camera),
                REQUEST_CAMERA_PERMISSION, Manifest.permission.CAMERA
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE == requestCode) {
            if (hasCameraPermission()) {
                showToast("permission granted")
            }
        }
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
                    //Some error handling
                    error.printStackTrace()
                }

                override fun onCanceled(@NonNull source: MediaSource) {
                    //Not necessary to remove any files manually anymore
                }
            })
    }

    private fun onPhotosReturned(path: Array<MediaFile>, source: MediaSource) {
        path.firstOrNull()?.let { mediaFile ->
            val ext = mediaFile.file.extension
            if (!ext.isBlank()) {
                when (ext) {
                    "png", "jpg", "jpeg" -> onImageReturn(mediaFile)
                    else -> viewModel.state.toast = "Invalid file found"
                }
            } else {
                viewModel.state.toast = "Invalid file found"
            }
        }
    }

    abstract fun onImageReturn(mediaFile: MediaFile)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.CAMERA)
    }
}