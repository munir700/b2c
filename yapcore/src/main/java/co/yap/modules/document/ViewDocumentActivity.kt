package co.yap.modules.document

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.modules.document.enums.FileFrom
import co.yap.modules.document.enums.FileType
import co.yap.modules.document.enums.TakePhotoType
import co.yap.widgets.bottomsheet.TakePhotoBottomSheet
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingImageActivity
import co.yap.yapcore.R
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.databinding.ActivityViewDocumentBinding
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.FileUtils
import co.yap.yapcore.helpers.extentions.*
import kotlinx.android.synthetic.main.activity_view_document.view.*
import pl.aprilapps.easyphotopicker.MediaFile
import co.yap.yapcore.interfaces.BackPressImpl

class ViewDocumentActivity : BaseBindingImageActivity<IViewDocumentActivity.ViewModel>(),
    IViewDocumentActivity.View {

    companion object {
        private const val LINK = "LINK"
        private const val FILETYPE = "FILETYPE"
        private const val FILEFROM = "FILEFROM"

        fun newIntent(
            context: Context,
            link: String,
            fileType: String,
            fileFrom: String
        ): Intent {
            val intent = Intent(context, ViewDocumentActivity::class.java)
            intent.putExtra(LINK, link)
            intent.putExtra(FILEFROM, fileFrom)
            intent.putExtra(FILETYPE, fileType)
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
        viewModel.clickEvent.observe(this, listener)
    }

    private fun setupData() {
        val link = intent?.getValue(LINK, ExtraType.STRING.name) as? String
        val fileFrom = intent?.getValue(FILEFROM, ExtraType.STRING.name) as? String
        val fileType = intent?.getValue(FILETYPE, ExtraType.STRING.name) as? String
        viewModel.state.isNeedToShowOnlyUpdateOption?.value = !link.isNullOrEmpty()
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
                if (viewModel.state.isNeedToShowOnlyUpdateOption.value == true) {
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
                viewDataBinding?.root?.iviImage?.setImageResource(0)
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
                                if (FileUtils.getFile(context, uriIntent.data).sizeInMb() <= 25) {
                                    loadFileInView(
                                        FileUtils.getFile(context, uriIntent.data).extension,
                                        FileFrom.Local().local,
                                        FileUtils.getFile(context, uriIntent.data).absolutePath
                                    )
                                    viewModel.state.isNeedToShowOnlyUpdateOption?.value = false
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
        title?.let { builder.setTitle(title) }
        val dialogLayout: View =
            inflater.inflate(R.layout.alert_upload_document_custom_dialogue, null)
        val tvDialogTitle = dialogLayout.findViewById<TextView>(co.yap.yapcore.R.id.tvDialogTitle)
        tvDialogTitle.text = getString(R.string.screen_upload_document_display_text_alert_title)
        val label = dialogLayout.findViewById<TextView>(co.yap.yapcore.R.id.tvTitle)
        label.text = getString(R.string.screen_upload_document_display_text_alert_message)
        val ok = dialogLayout.findViewById<TextView>(co.yap.yapcore.R.id.tvButtonTitle)
        ok.text = getString(R.string.screen_upload_document_display_text_alert_got_it)
        val cancel = dialogLayout.findViewById<TextView>(co.yap.yapcore.R.id.tvButtonCancel)
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

        val intent = Intent()
        intent.putExtra(ExtraKeys.FILE_PATH.name, viewModel.state.filePath?.value)
        intent.putExtra(ExtraKeys.FILE_TYPE.name, viewModel.state.fileType?.value)
        intent.putExtra(ExtraKeys.FILE_UPDATED.name, viewModel.state.isFileUpdated.value)
        setResult(RequestCodes.REQUEST_VIEW_DOCUMENT, intent)

        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun close() {
        showToast("Invalid file")
        finish()
    }

    override fun onDestroy() {
        context.deleteTempFolder()
        super.onDestroy()
    }

    override fun onImageReturn(mediaFile: MediaFile) {
        if (mediaFile.file.sizeInMb() <= 25) {
            loadFileInView(
                mediaFile.file.extension,
                FileFrom.Local().local,
                mediaFile.file.absolutePath
            )
            viewModel.state.isNeedToShowOnlyUpdateOption?.value = false
            viewModel.state.isFileUpdated.value = true
        } else {
            showToast("Your file size is too big. Please upload a file less than 25MB to proceed")
        }
    }

    private fun setImageResUrl(imageSrc: String?) {
        viewModel.state.isPDF.value = false
        viewModel.state.imageUrlForImageView?.value = imageSrc
    }
}
