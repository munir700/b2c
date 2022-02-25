package co.yap.modules.document

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.R
import co.yap.databinding.ActivityViewDocumentBinding
import co.yap.databinding.ActivityViewDocumentBindingImpl
import co.yap.modules.document.enums.FileFrom
import co.yap.modules.document.enums.FileType
import co.yap.modules.document.enums.TakePhotoType
import co.yap.widgets.bottomsheet.TakePhotoBottomSheet
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingImageActivity
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.FileUtils
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.interfaces.BackPressImpl
import kotlinx.android.synthetic.main.activity_view_document.view.*
import pl.aprilapps.easyphotopicker.MediaFile

class ViewDocumentActivity : BaseBindingImageActivity<IViewDocumentActivity.ViewModel>(),
    IViewDocumentActivity.View {

    companion object {
        private const val LINK = "LINK"
        private const val FILETYPE = "FILETYPE"
        private const val FILEFROM = "FILEFROM"
        private const val ISNEEDTOUPDATEFILE = "ISNEEDTOUPDATEFILE"

        fun newIntent(
            context: Context,
            link: String,
            fileType: String,
            fileFrom: String,
            isNeedToUpdateFile: Boolean
        ): Intent {
            val intent = Intent(context, ViewDocumentActivity::class.java)
            intent.putExtra(LINK, link)
            intent.putExtra(FILEFROM, fileFrom)
            intent.putExtra(FILETYPE, fileType)
            intent.putExtra(ISNEEDTOUPDATEFILE, isNeedToUpdateFile)
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
        val filetype = intent?.getValue(FILETYPE, ExtraType.STRING.name) as? String
        val isNeedToUpdateFile = intent?.getBooleanExtra(ISNEEDTOUPDATEFILE, false)

        viewModel.state.fileType?.value = filetype
        viewModel.state.isNeedToUpdateFile?.value = isNeedToUpdateFile ?: false

        loadFileInView(filetype, fileFrom, link)
    }

    fun loadFileInView(fileType: String?, fileFrom: String?, link: String?) {
        when (fileFrom) {
            FileFrom.Link().link -> {
                if (fileType == FileType.PDF().pdf) {
                    viewModel.state.isPDF.value = true

                    link?.let {
                        viewModel.downloadFile(it) { file ->
                            file?.let {
                                viewDataBinding?.root?.pdfView?.fromFile(file)?.show()
                            }
                        }
                    } ?: close()
                } else {
                    viewModel.state.isPDF.value = false
                    viewModel.state.ImageUrlForImageView?.value = link
                }
            }
            FileFrom.Local().local -> {

                if (fileType == FileType.PDF().pdf) {
                    viewModel.state.isPDF.value = true
                    link?.let {
                        viewDataBinding?.root?.pdfView?.fromFile(link)?.show()
                    }
                } else {
                    viewModel.state.isPDF.value = false
                    viewModel.state.ImageUrlForImageView?.value = link
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
                if (viewModel.state.isNeedToUpdateFile.value == true) {
                    uploadAlert()
                } else {
                    showDialogueOptions()
                }
            }
            R.id.ivdelete -> {
//                viewDataBinding?.root?.pdfView?.fromFile(null)?.show()
                viewModel.state.filePath?.value = null
                viewModel.state.ImageUrlForImageView?.value = null
                viewModel.state.fileType?.value = null
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
                                    viewModel.state.filePath?.value =
                                        FileUtils.getFile(context, uriIntent.data).absolutePath
                                    loadFileInView(
                                        FileUtils.getFile(context, uriIntent.data).extension,
                                        FileFrom.Local().local,
                                        FileUtils.getFile(context, uriIntent.data).absolutePath
                                    )
                                } else {
                                    showToast("You can select 25 mb size file only")
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
            inflater.inflate(co.yap.yapcore.R.layout.alert_upload_document_custom_dialogue, null)
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
            viewModel.state.filePath?.value = mediaFile.file.absolutePath
            loadFileInView(
                mediaFile.file.extension,
                FileFrom.Local().local,
                mediaFile.file.absolutePath
            )
        } else {
            showToast("You can select 25 mb size file only")
        }
    }
}

