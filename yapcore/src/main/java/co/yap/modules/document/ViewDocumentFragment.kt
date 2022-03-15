package co.yap.modules.document

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.modules.document.enums.FileFrom
import co.yap.modules.document.enums.FileType
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingImageFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentViewDocumentBinding
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.FileUtils
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.showAlertDialogAndExitApp
import kotlinx.android.synthetic.main.activity_view_document.view.*
import pl.aprilapps.easyphotopicker.MediaFile
import co.yap.yapcore.interfaces.OnItemClickListener

class ViewDocumentFragment : BaseBindingImageFragment<IViewDocumentFragment.ViewModel>(),
    IViewDocumentFragment.View {


    override val viewModel: IViewDocumentFragment.ViewModel
        get() = ViewModelProvider(this).get(IViewDocumentViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_view_document

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        getDataBindingView<FragmentViewDocumentBinding>().lifecycleOwner = this
    }

    private fun setupData() {
        arguments?.let {
            val link = it.getString("LINK", ExtraType.STRING.name)
            val fileFrom = it.getString("FILEFROM", ExtraType.STRING.name)
            val fileType = it.getString("FILETYPE", ExtraType.STRING.name)
            val isEditAble = it.getBoolean("ISEDITABLE", false)
            when (fileFrom) {
                FileFrom.Link().link -> {
                    viewModel.state.isNeedToShowUpdateDialogue?.value = !link.isNullOrEmpty()
                    editable(isEditAble, false)
                }
                FileFrom.Local().local -> {
                    editable(isEditAble, true)
                }
            }
            loadFileInView(fileType, fileFrom, link)
        }
    }

    fun loadFileInView(fileType: String?, fileFrom: String?, link: String?) {
        viewModel.state.fileType?.value = fileType
        viewModel.state.filePath?.value = link
        when (fileFrom) {
            FileFrom.Link().link -> {
                if (fileType?.contains(FileType.PDF().pdf) == true) {
                    imageNeededShow(false)
                    link?.let {
                        viewModel.downloadFile(it) { file ->
                            file?.let {
                                refreshViewNeedTOShow(false)
                                viewDataBinding?.root?.pdfView?.fromFile(file)?.show()
                            }
                        }
                    } ?: close()
                } else {
                    setImageResUrl(link)
                }
            }
            FileFrom.Local().local -> {
                if (fileType?.contains(FileType.PDF().pdf) == true) {
                    imageNeededShow(false)
                    link?.let {
                        viewDataBinding?.root?.pdfView?.fromFile(link)?.show()
                    }
                } else {
                    setImageResUrl(link)
                }
            }
        }
    }

    val listener = Observer<Int> {
        when (it) {
            R.id.ivCancel -> {
                onBackPressed()
            }
            R.id.ivUpdate -> {
                if (viewModel.state.isNeedToShowUpdateDialogue.value == true) {
                    uploadAlert()
                } else {
                    showDialogueOptions()
                }
            }
            R.id.ivdelete -> {
                performDelete()
            }
            R.id.btnRefresh -> {
                if (viewModel.state.fileType?.value?.contains(FileType.PDF().pdf) == true) {
                    refreshViewNeedTOShow(false)
                    viewModel.state.filePath?.value?.let { it ->
                        viewModel.downloadFile(it) { file ->
                            file?.let {
                                viewDataBinding?.root?.pdfView?.fromFile(file)?.show()
                            }
                        }
                    } ?: close()
                }
            }
        }
    }

    private fun showDialogueOptions() {
        activity?.launchSheet(
            itemClickListener = itemListener,
            itemsList = viewModel.getUploadDocumentOptions(),
            heading = getString(R.string.choose_from_library_display_text_title)
        )
    }

    private val itemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when ((data as BottomSheetItem).tag) {
                PhotoSelectionType.CAMERA.name -> {
                    openImagePicker(PhotoSelectionType.CAMERA)
                }
                PhotoSelectionType.GALLERY.name -> {
                    requireActivity().openFilePicker("File picker",
                        completionHandler = { _, dataUri ->
                            dataUri?.let { uriIntent ->
                                var fileSelected = FileUtils.getFile(context, uriIntent.data)
                                if (fileSelected.sizeInMb() < 25) {
                                    var fileAfterBrowse =
                                        context?.createTempFileForBrowse(fileSelected.extension)
                                    fileAfterBrowse?.let {
                                        fileSelected.copyTo(it)
                                        viewModel.fileForUpdate = it
                                        loadFileInView(
                                            it.extension,
                                            FileFrom.Local().local,
                                            it.absolutePath
                                        )
                                        viewModel.state.isNeedToShowUpdateDialogue?.value =
                                            false
                                        deletable(true)
                                    }

                                } else {
                                    showToast(Strings.screen_view_document_file_size_not_fine)
                                }
                            }
                        })
                }
            }
        }
    }

    private fun uploadAlert() {
        activity?.let {
            it.showAlertDialogAndExitApp(
                dialogTitle = getString(R.string.screen_upload_document_display_text_alert_title),
                message = getString(R.string.screen_upload_document_display_text_alert_message),
                leftButtonText = getString(R.string.screen_upload_document_display_text_alert_got_it),
                callback = {
                    showDialogueOptions()
                },
                titleVisibility = true,
                closeActivity = false,
                isTwoButton = true
            )
        }
    }

    override fun onBackPressed(): Boolean {
        if (viewModel.state.isDeleteAble.value == true) {
            val intent = Intent()
            intent.putExtra(ExtraKeys.FILE_FOR_UPDATE.name, viewModel.fileForUpdate)
            activity?.setResult(Activity.RESULT_OK, intent)
        }
        activity?.finish()
        return true
    }

    private fun close() {
        refreshViewNeedTOShow(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onImageReturn(mediaFile: MediaFile) {
        if (mediaFile.file.sizeInMb() < 25) {
            viewModel.fileForUpdate = mediaFile.file
            loadFileInView(
                mediaFile.file.extension,
                FileFrom.Local().local,
                mediaFile.file.absolutePath
            )
            viewModel.state.isNeedToShowUpdateDialogue?.value = false
            deletable(true)
        } else {
            showToast(Strings.screen_view_document_file_size_not_fine)
        }
    }

    private fun setImageResUrl(imageSrc: String?) {
        imageNeededShow(true)
        viewModel.state.imageUrlForImageView?.value = imageSrc
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(listener)
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, listener)
    }

    fun refreshViewNeedTOShow(isVisible: Boolean) {
        if (!isVisible) {
            viewModel.state.isNeedToRefreshView.value = false
        } else {
            viewModel.state.isNeedToRefreshView.value = true
            viewModel.state.isPDF.value = false
            viewModel.state.isImage.value = false
        }
    }

    fun performDelete() {
        context?.let { it.deleteTempFolder() }
        viewModel.state.filePath?.value = null
        viewModel.state.imageUrlForImageView?.value = null
        viewModel.state.fileType?.value = null
        deletable(false)
        imageNeededShow(true)
        viewDataBinding?.root?.iviImage?.setImageResource(0)
        refreshViewNeedTOShow(false)
    }

    fun imageNeededShow(isImage: Boolean) {
        if (!isImage) {
            viewModel.state.isImage.value = false
            viewModel.state.isPDF.value = true
        } else {
            viewModel.state.isPDF.value = false
            viewModel.state.isImage.value = true
        }
    }

    fun deletable(isDeleteAble: Boolean) {
        if (isDeleteAble) {
            viewModel.state.isDeleteAble.value = true
            viewModel.state.isUpdateAble.value = false
        } else {
            viewModel.state.isDeleteAble.value = false
            viewModel.state.isUpdateAble.value = true
        }
    }

    fun editable(isEditAble: Boolean, isDeletable: Boolean) {
        if (!isEditAble) {
            deletable(isDeletable)
            viewModel.state.isDeleteAble?.value = false
            viewModel.state.isUpdateAble?.value = false
        } else {
            deletable(isDeletable)
        }
        viewModel.state.isEditable?.value = isEditAble
    }

}
