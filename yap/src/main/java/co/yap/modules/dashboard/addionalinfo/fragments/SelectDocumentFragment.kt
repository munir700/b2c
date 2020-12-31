package co.yap.modules.dashboard.addionalinfo.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.modules.dashboard.addionalinfo.model.AdditionalDocumentImage
import co.yap.modules.dashboard.addionalinfo.viewmodels.SelectDocumentViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.more.profile.fragments.UpdatePhotoBottomSheet
import co.yap.modules.others.helper.Constants
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument
import co.yap.translation.Strings
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.AdditionalInfoScreenType
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.interfaces.OnItemClickListener
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class SelectDocumentFragment : AdditionalInfoBaseFragment<ISelectDocument.ViewModel>(),
    ISelectDocument.View, CardClickListener, EasyPermissions.PermissionCallbacks {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_select_document
    private lateinit var updatePhotoBottomSheet: UpdatePhotoBottomSheet
    private val takePhoto = 1
    private val pickPhoto = 2
    internal var permissionHelper: PermissionHelper? = null
    private var currentPos: Int? = null
    lateinit var easyImage: EasyImage
    private var currentDocument: AdditionalDocument? = null

    override val viewModel: SelectDocumentViewModel
        get() = ViewModelProviders.of(this).get(SelectDocumentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showHeader(true)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        viewModel.uploadAdditionalDocumentAdapter.allowFullItemClickListener = true
        viewModel.uploadAdditionalDocumentAdapter.setItemListener(listener)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AdditionalDocument) {
                if (data.status == "PENDING") {
                    currentDocument = data
                    currentPos = pos
                    openBottomSheet(data.name)
                }
            }
        }
    }

    private fun openBottomSheet(name: String?) {
        this.fragmentManager?.let {
            updatePhotoBottomSheet = UpdatePhotoBottomSheet(
                mListener = this,
                showRemove = false,
                title = name + " " + getString(Strings.common_display_text_copy),
                subTitle = getString(Strings.screen_additional_info_label_text_bottom_sheet_des) + " " + name
            )
            updatePhotoBottomSheet.show(it, "")
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                if (viewModel.getScreenType() == AdditionalInfoScreenType.BOTH_SCREENS.name) {
                    viewModel.moveToNext()
                    navigate(R.id.action_selectDocumentFragment_to_additionalInfoQuestion)
                } else {
                    navigate(R.id.action_selectDocumentFragment_to_additionalInfoComplete)
                }
            }
            R.id.tvDoItLater -> {
                requireActivity().finish()
            }
        }
    }

    override fun onClick(eventType: Int) {
        updatePhotoBottomSheet.dismiss()

        when (eventType) {
            Constants.EVENT_ADD_PHOTO -> {
                openScanDocumentFragment(currentDocument?.name ?: "")
            }
            Constants.EVENT_CHOOSE_PHOTO -> {
                initEasyImage(pickPhoto)
            }
            Constants.EVENT_REMOVE_PHOTO -> {

            }
        }
    }

    private fun openScanDocumentFragment(fileName: String) {
        startFragmentForResult<AdditionalInfoScanDocumentFragment>(
            fragmentName = AdditionalInfoScanDocumentFragment::class.java.name,
            bundle = bundleOf(
                AdditionalDocumentImage::class.java.name to AdditionalDocumentImage(
                    name = fileName,
                    file = File(fileName)
                )
            )
        ) { resultCode, intent ->
            if (resultCode == Activity.RESULT_OK) {
                intent?.let {
                    val file: File? = it.extras?.get("file") as File
                    uploadDocumentAndMoveNext(
                        file,
                        currentPos ?: 0,
                        currentDocument ?: AdditionalDocument()
                    )
                }
            }
        }
    }

    private fun uploadDocumentAndMoveNext(file: File?, pos: Int, data: AdditionalDocument) {
        file?.let {
            viewModel.uploadDocument(
                file,
                data.documentType ?: ""
            ) {
                data.status = if (data.status == "PENDING") "DONE" else "PENDING"
                viewModel.uploadAdditionalDocumentAdapter.setItemAt(
                    pos,
                    data
                )
                viewModel.setEnabled(viewModel.uploadAdditionalDocumentAdapter.getDataList()) {
                    viewModel.setSubTitle(it)
                }
            }
        } ?: showToast("Invalid Image")
    }


    private fun initEasyImage(type: Int) {
        if (hasCameraPermission()) {
            easyImage = EasyImage.Builder(requireContext())
                .setChooserTitle("Pick Image")
                .setFolderName("YAPImage")
                .allowMultiple(false)
                .build()
            when (type) {
                takePhoto -> {
                    easyImage.openCameraForImage(this)

                }
                pickPhoto -> {
                    easyImage.openGallery(this)
                }
            }
            //  easyImage.openChooser(this)
        } else {
            EasyPermissions.requestPermissions(
                this, "This app needs access to your camera so you can take pictures.",
                RequestCodes.REQUEST_CAMERA_PERMISSION, Manifest.permission.CAMERA
            )
        }

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
                        uploadDocumentAndMoveNext(
                            mediaFile.file,
                            currentPos ?: 0,
                            currentDocument ?: AdditionalDocument()
                        )
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

    override fun onBackPressed(): Boolean {
        return true
    }
}