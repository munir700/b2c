package co.yap.modules.dashboard.addionalinfo.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.modules.dashboard.addionalinfo.model.AdditionalDocumentImage
import co.yap.modules.dashboard.addionalinfo.viewmodels.SelectDocumentViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.more.profile.fragments.UpdatePhotoBottomSheet
import co.yap.modules.others.helper.Constants
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument
import co.yap.yapcore.enums.AdditionalInfoScreenType
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.interfaces.OnItemClickListener
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class SelectDocumentFragment : AdditionalInfoBaseFragment<ISelectDocument.ViewModel>(),
    ISelectDocument.View, CardClickListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_select_document
    private lateinit var updatePhotoBottomSheet: UpdatePhotoBottomSheet
    private val takePhoto = 1
    private val pickPhoto = 2
    internal var permissionHelper: PermissionHelper? = null
    private var currentPos: Int? = null
    private var currentDocument: AdditionalDocument? = null

    override val viewModel: SelectDocumentViewModel
        get() = ViewModelProviders.of(this).get(SelectDocumentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        viewModel.uploadAdditionalDocumentAdapter.allowFullItemClickListener = true
        viewModel.uploadAdditionalDocumentAdapter.setItemListener(listener)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AdditionalDocument) {
                if (data.isUploaded == false) {
                    currentDocument = data
                    currentPos = pos
                    openBottomSheet()
                }
            }
        }
    }

    private fun openBottomSheet() {
        this.fragmentManager?.let {
            updatePhotoBottomSheet = UpdatePhotoBottomSheet(mListener = this, showRemove = false)
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
                    startFragment(
                        fragmentName = AdditionalInfoCompleteFragment::class.java.name,
                        clearAllPrevious = true
                    )
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
                checkPermission(pickPhoto)
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
                data.isUploaded = !(data.isUploaded ?: false)
                viewModel.uploadAdditionalDocumentAdapter.setItemAt(
                    pos,
                    data
                )
                viewModel.setEnabled(viewModel.uploadAdditionalDocumentAdapter.getDataList())
            }
        } ?: showToast("Invalid Image")
    }


    private fun checkPermission(type: Int) {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.CAMERA
            ), 100
        )

        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
//                if (type == takePhoto) {
//                    EasyImage.openCamera(this@SelectDocumentFragment, takePhoto)
//                } else {
                EasyImage.openGallery(this@SelectDocumentFragment, pickPhoto)
//                }

            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
//                if (type == takePhoto) {
                if (grantedPermission.contains(Manifest.permission.CAMERA))
                    EasyImage.openCamera(this@SelectDocumentFragment, takePhoto)
//                }
//                else {
//                    if (grantedPermission.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//                        EasyImage.openGallery(this@SelectDocumentFragment, pickPhoto)
//                }
            }

            override fun onPermissionDenied() {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDeniedBySystem() {
                permissionHelper?.openAppDetailsActivity()
            }
        })
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
                    onPhotosReturned(imageFile, type, source)
                }

                override fun onImagePickerError(
                    e: Exception?,
                    source: EasyImage.ImageSource?,
                    type: Int
                ) {
                    viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                }
            })
    }

    private fun onPhotosReturned(path: File?, position: Int, source: EasyImage.ImageSource?) {
        path?.let {
            val ext = path.extension
            if (!ext.isBlank()) {
                when (ext) {
                    "png", "jpg", "jpeg" -> {
                        uploadDocumentAndMoveNext(
                            path,
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
    }
}