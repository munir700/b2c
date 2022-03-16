package co.yap.modules.document

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.modules.document.enums.FileFrom
import co.yap.modules.document.enums.FileType
import co.yap.translation.Strings
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingImageFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentViewDocumentBinding
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.FileUtils
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.glide.getUrl
import co.yap.yapcore.helpers.showAlertDialogAndExitApp
import pl.aprilapps.easyphotopicker.MediaFile
import co.yap.yapcore.interfaces.OnItemClickListener
import com.liveperson.infra.utils.picasso.Callback
import com.liveperson.infra.utils.picasso.MemoryPolicy
import com.liveperson.infra.utils.picasso.NetworkPolicy
import com.liveperson.infra.utils.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_view_document.*
import kotlinx.android.synthetic.main.fragment_view_document.view.*
import kotlinx.android.synthetic.main.layout_loading_view_for_view_document.view.*

class ViewDocumentFragment : BaseBindingImageFragment<IViewDocumentFragment.ViewModel>(),
    IViewDocumentFragment.View {

    override val viewModel: IViewDocumentViewModel
        get() = ViewModelProvider(this).get(IViewDocumentViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_view_document

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedWithData()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataBindingView<FragmentViewDocumentBinding>().lifecycleOwner = this
        if (context?.isNetworkAvailable() == true) {
            getDataFromIntent()
            viewModel.state.stateLiveData?.observe(viewLifecycleOwner, Observer { handleState(it) })
            viewDataBinding?.root?.multiStateView.setOnReloadListener(object :
                MultiStateView.OnReloadListener {
                override fun onReload(view: View) {
                    if (context?.isNetworkAvailable() == true) {
                        viewModel.getEmploymentInfoApiCall { fileType, link ->
                            setupData(
                                FileFrom.Link().link,
                                link ?: "",
                                viewModel.state.isEditable.value ?: false,
                                fileType ?: ""
                            )
                        }
                    } else {
                        showInternetSnack(true)
                    }
                }
            })
        } else {
            showInternetSnack(true)
        }
    }

    private fun getDataFromIntent() {
        arguments?.let {
            val link = it.getString("LINK", ExtraType.STRING.name)
            val fileType = it.getString("FILETYPE", ExtraType.STRING.name)
            val isEditAble = it.getBoolean("ISEDITABLE", false)
            viewModel.state.documentType.value = it.getString("DOCUMENTTYPE", ExtraType.STRING.name)
            val fileFrom = if (link.contains("http")) {
                FileFrom.Link().link
            } else {
                FileFrom.Local().local
            }
            setupData(fileFrom, link, isEditAble, fileType)
        }
    }

    fun setupData(fileFrom: String, link: String, isEditAble: Boolean, fileType: String) {
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
                onBackPressedWithData()
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
        }
    }

    private fun showDialogueOptions() {
        viewModel.state.stateLiveData?.postValue(State.success(""))
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
                                    fileSelected?.let {
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
                                    showToast(getString(R.string.screen_view_document_file_size_not_fine))
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

    fun onBackPressedWithData() {
        if (viewModel.state.isDeleteAble.value == true && viewModel.fileForUpdate != null) {
            val intent = Intent()
            intent.putExtra(ExtraKeys.FILE_FOR_UPDATE.name, viewModel.fileForUpdate)
            activity?.setResult(Activity.RESULT_OK, intent)
        }
        activity?.finish()
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
            showToast(getString(R.string.screen_view_document_file_size_not_fine))
        }
    }

    private fun setImageResUrl(imageSrc: String?) {
        viewModel.state.stateLiveData?.postValue(State.loading(""))
        imageNeededShow(true)
        imageSrc?.let {
            var mUrl = getUrl(imageSrc)
            if (!mUrl.contains("http")) {
                mUrl = "file://$mUrl"
            }
            Picasso.get()
                .load(mUrl)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewDataBinding?.root?.iviImage, object : Callback {
                    override fun onSuccess() {
                        viewModel.state.stateLiveData?.postValue(State.success(""))
                    }

                    override fun onError(e: java.lang.Exception?) {
                        viewModel.state.stateLiveData?.postValue(State.error(getString(Strings.screen_view_document_refresh_text_description)))
                    }
                })
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(listener)
        viewModel.state.stateLiveData?.removeObservers(this)
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, listener)
    }

    fun refreshViewNeedTOShow(isVisible: Boolean) {
        if (isVisible) {
            viewModel.state.stateLiveData?.postValue(State.error(getString(Strings.screen_view_document_refresh_text_description)))
        } else {
            viewModel.state.stateLiveData?.postValue(State.success(""))
        }
    }

    fun performDelete() {
        viewModel.state.filePath?.value = null
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

    private fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> {
                multiStateView?.viewState = MultiStateView.ViewState.LOADING
                multiStateView?.circularProgressBar?.indeterminateMode = true
            }
            Status.EMPTY -> {
                multiStateView?.viewState = MultiStateView.ViewState.EMPTY
            }
            Status.ERROR -> {
                multiStateView?.viewState = MultiStateView.ViewState.ERROR
            }
            else ->
                multiStateView?.viewState = MultiStateView.ViewState.CONTENT
        }
    }

}
