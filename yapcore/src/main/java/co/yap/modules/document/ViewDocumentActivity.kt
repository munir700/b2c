package co.yap.modules.document

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.modules.document.enums.FileFrom
import co.yap.modules.document.enums.FileType
import co.yap.modules.document.enums.TakePhotoType
import co.yap.widgets.bottomsheet.TakePhotoBottomSheet
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingImageActivity
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ActivityViewDocumentBinding
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.FileUtils
import co.yap.yapcore.helpers.extentions.*
import kotlinx.android.synthetic.main.activity_view_document.view.*
import pl.aprilapps.easyphotopicker.MediaFile
import co.yap.yapcore.interfaces.BackPressImpl
import kotlinx.android.synthetic.main.alert_dialogue.*

class ViewDocumentActivity : BaseBindingImageActivity<IViewDocumentActivity.ViewModel>(),
    IViewDocumentActivity.View {

    companion object {
        private const val LINK = "LINK"
        private const val FILETYPE = "FILETYPE"
        private const val FILEFROM = "FILEFROM"
        private const val ISEDITABLE = "ISEDITABLE"

        fun newIntent(
            context: Context,
            link: String,
            fileType: String,
            fileFrom: String,
            isEditAble: Boolean
        ): Intent {
            val intent = Intent(context, ViewDocumentActivity::class.java)
            intent.putExtra(LINK, link)
            intent.putExtra(FILEFROM, fileFrom)
            intent.putExtra(FILETYPE, fileType)
            intent.putExtra(ISEDITABLE, isEditAble)
            return intent
        }
    }

    override val viewModel: IViewDocumentActivity.ViewModel
        get() = ViewModelProvider(this).get(IViewDocumentViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_view_document

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupData()
        getDataBindingView<ActivityViewDocumentBinding>().lifecycleOwner = this
        addObservers()
    }

    private fun setupData() {
        val link = intent?.getValue(LINK, ExtraType.STRING.name) as? String
        val fileFrom = intent?.getValue(FILEFROM, ExtraType.STRING.name) as? String
        val fileType = intent?.getValue(FILETYPE, ExtraType.STRING.name) as? String
        val isEditAble = intent?.getValue(ISEDITABLE, ExtraType.BOOLEAN.name) as? Boolean
        when (fileFrom) {
            FileFrom.Link().link -> {
                viewModel.state.isNeedToShowUpdateDialogue?.value = !link.isNullOrEmpty()
                viewModel.state.isDeleteAble?.value = false
                viewModel.state.isUpdateAble?.value = true
            }
            FileFrom.Local().local -> {
                viewModel.state.isDeleteAble?.value = true
                viewModel.state.isUpdateAble?.value = false
            }
        }
        viewModel.state.isEditable?.value = isEditAble
        loadFileInView(fileType, fileFrom, link)

    }

    fun loadFileInView(fileType: String?, fileFrom: String?, link: String?) {
        viewModel.state.fileType?.value = fileType
        viewModel.state.filePath?.value = link
        when (fileFrom) {
            FileFrom.Link().link -> {
                if (fileType?.contains(FileType.PDF().pdf) == true) {
                    viewModel.state.isPDF.value = true

                    link?.let {
                        viewModel.downloadFile(it) { file ->
                            file?.let {
                                viewModel.state.isNeedToRefreshView.value = false
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
                    viewModel.state.isPDF.value = true
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
            R.id.ivshare -> {
                if (viewModel.state.isNeedToShowUpdateDialogue.value == true) {
                    uploadAlert()
                } else {
                    showDialogueOptions()
                }
            }
            R.id.ivdelete -> {
                viewModel.state.filePath?.value = null
                viewModel.state.imageUrlForImageView?.value = null
                viewModel.state.fileType?.value = null
                viewModel.state.isPDF.value = false
                viewModel.state.isFileUpdated.value = false
                viewModel.state.isDeleteAble.value = false
                viewModel.state.isUpdateAble.value = true
                viewDataBinding?.root?.iviImage?.setImageResource(0)
                viewModel.state.isNeedToRefreshView.value = false
            }
            R.id.btnRefresh -> {
                if (viewModel.state.fileType?.value?.contains(FileType.PDF().pdf) == true) {
                    viewModel.state.isNeedToRefreshView.value = false
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
        launchTakePhotoSheet(
            itemClickListener = onBottomSheetClickListener,
            heading = getString(R.string.choose_from_library_display_text_title)
        )
    }

    private val onBottomSheetClickListener = object :
        TakePhotoBottomSheet.OnTakePhotoBottomSheetItemClickListener {
        override fun onItemClick(viewId: Int) {
            when (viewId) {
                TakePhotoType.TakePhoto().tvTakePhoto -> {
                    openImagePicker(PhotoSelectionType.CAMERA)
                }
                TakePhotoType.Browse().tvbrowseFiles -> {
                    this@ViewDocumentActivity.openFilePicker("File picker",
                        completionHandler = { _, dataUri ->
                            dataUri?.let { uriIntent ->
                                var fileSelected = FileUtils.getFile(context, uriIntent.data)
                                if (fileSelected.sizeInMb() <= 25) {
                                    var fileAfterBrowse = context.createTempFileForBrowse(fileSelected.extension)
                                    fileSelected.copyTo(fileAfterBrowse)
                                    viewModel.fileForUpdate = fileAfterBrowse
                                    loadFileInView(
                                        fileAfterBrowse.extension,
                                        FileFrom.Local().local,
                                        fileAfterBrowse.absolutePath
                                    )
                                    viewModel.state.isNeedToShowUpdateDialogue?.value = false
                                    viewModel.state.isDeleteAble?.value = true
                                    viewModel.state.isUpdateAble?.value = false
                                    viewModel.state.isFileUpdated.value = true
                                } else {
                                    showToast("Your file size is too big. Please upload a file less than 25MB to proceed")
                                }
                            }
                        })
                }
            }
        }
    }

    private fun uploadAlert() {

        val builder = android.app.AlertDialog.Builder(this)
        var alertDialog: android.app.AlertDialog? = null
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogLayout: View =
            inflater.inflate(R.layout.alert_upload_document_custom_dialogue, null)
        val tvDialogTitle = dialogLayout.findViewById<TextView>(co.yap.yapcore.R.id.tvDialogTitle)
        tvDialogTitle.text = getString(R.string.screen_upload_document_display_text_alert_title)
        val label = dialogLayout.findViewById<TextView>(R.id.tvTitle)
        label.text = getString(R.string.screen_upload_document_display_text_alert_message)
        val ok = dialogLayout.findViewById<TextView>(R.id.tvButtonTitle)
        ok.text = getString(R.string.screen_upload_document_display_text_alert_got_it)
        val cancel = dialogLayout.findViewById<TextView>(R.id.tvButtonCancel)
        cancel.text = getString(R.string.common_button_cancel)

        ok.setOnClickListener {
            showDialogueOptions()
            alertDialog?.dismiss()
        }
        cancel.setOnClickListener {
            alertDialog?.dismiss()
        }
        builder.setView(dialogLayout)
        builder.setCancelable(false)
        alertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
    }

    override fun onBackPressed() {
        if (viewModel.state.isFileUpdated.value == true) {
            val intent = Intent()
            intent.putExtra(ExtraKeys.FILE_FOR_UPDATE.name, viewModel.fileForUpdate)
            intent.putExtra(ExtraKeys.FILE_PATH.name, viewModel.state.filePath?.value)
            intent.putExtra(ExtraKeys.FILE_TYPE.name, viewModel.state.fileType?.value)
            setResult(Activity.RESULT_OK, intent)
        }
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun close() {
        viewModel.state.isNeedToRefreshView.value = true
    }

    override fun onDestroy() {
        context.deleteTempFolder()
        super.onDestroy()
        removeObservers()
    }

    override fun onImageReturn(mediaFile: MediaFile) {
        if (mediaFile.file.sizeInMb() <= 25) {
            viewModel.fileForUpdate = mediaFile.file
            loadFileInView(
                mediaFile.file.extension,
                FileFrom.Local().local,
                mediaFile.file.absolutePath
            )
            viewModel.state.isNeedToShowUpdateDialogue?.value = false
            viewModel.state.isDeleteAble?.value = true
            viewModel.state.isUpdateAble?.value = false
            viewModel.state.isFileUpdated.value = true
        } else {
            showToast("Your file size is too big. Please upload a file less than 25MB to proceed")
        }
    }

    private fun setImageResUrl(imageSrc: String?) {
        viewModel.state.isPDF.value = false
        viewModel.state.imageUrlForImageView?.value = imageSrc
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(listener)
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, listener)
    }
}
