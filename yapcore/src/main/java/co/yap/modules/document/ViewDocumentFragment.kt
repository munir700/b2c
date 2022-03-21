package co.yap.modules.document

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.networking.customers.responsedtos.employment_amendment.Document
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
        when {
            context?.isNetworkAvailable() == true -> {
                getDataFromIntent()
                viewModel.state.stateLiveData?.observe(
                    viewLifecycleOwner,
                    Observer { handleState(it) })
                getDataBindingView<FragmentViewDocumentBinding>().multiStateView.setOnReloadListener(
                    object :
                        MultiStateView.OnReloadListener {
                        override fun onReload(view: View) {
                            if (context?.isNetworkAvailable() == true) {
                                viewModel.getEmploymentInfoApiCall()
                            } else {
                                showInternetSnack(true)
                            }
                        }
                    })
            }
            else -> {
                showInternetSnack(true)
            }
        }
    }

    private fun getDataFromIntent() {
        arguments?.let { it ->
            viewModel.state.fileUrl.value = it.getString("LINK", ExtraType.STRING.name)
            viewModel.state.fileExtension.value =
                it.getString("FILEEXTENSTION", ExtraType.STRING.name)
            viewModel.state.isEditable.value = it.getBoolean("ISEDITABLE", false)
            viewModel.state.documentType.value = it.getString("DOCUMENTTYPE", ExtraType.STRING.name)
            viewModel.state.fileUrl.value?.let { fileUrl ->
                if (fileUrl.contains("http") && viewModel.state.isEditable.value == true) {
                    viewModel.state.isNeedToShowUpdateDialogue.value = true
                    isDeleteAble(false)
                } else {
                    isDeleteAble(true)
                }
                loadFileInView()
            }

        }
    }

    fun loadFileInView() {
        viewModel.state.fileUrl.let { fileUrl ->
            when {
                viewModel.state.fileExtension.value?.contains("pdf") == true -> {
                    when {
                        fileUrl.value?.contains("http") == true -> {
                            viewModel.downloadFile(fileUrl.value ?: "") { file ->
                                file?.let {
                                    refreshViewNeedTOShow(false)
                                    getDataBindingView<FragmentViewDocumentBinding>().pdfView.fromFile(
                                        file
                                    ).show()
                                } ?: close()

                            }
                        }
                        else -> {
                            fileUrl.let { file ->
                                getDataBindingView<FragmentViewDocumentBinding>().pdfView.fromFile(
                                    file.value ?: ""
                                ).show()
                            }
                        }
                    }
                }
                else -> {
                    setImageResUrl(viewModel.state.fileUrl.value)
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
            R.id.ivDelete -> {
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
                                val fileSelected = FileUtils.getFile(context, uriIntent.data)
                                if (fileSelected.sizeInMb() < 25) {
                                    fileSelected?.let {
                                        viewModel.fileForUpdate = it
                                        viewModel.state.fileUrl.value = it.absolutePath
                                        viewModel.state.fileExtension.value = it.extension
                                        loadFileInView()
                                        viewModel.state.isNeedToShowUpdateDialogue.value =
                                            false
                                        isDeleteAble(true)
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

    override fun onImageReturn(mediaFile: MediaFile) {
        if (mediaFile.file.sizeInMb() < 25) {
            viewModel.fileForUpdate = mediaFile.file
            viewModel.state.fileUrl.value = mediaFile.file.absolutePath
            viewModel.state.fileExtension.value = mediaFile.file.extension
            viewModel.state.isNeedToShowUpdateDialogue.value = false
            loadFileInView()
            isDeleteAble(true)
        } else {
            showToast(getString(R.string.screen_view_document_file_size_not_fine))
        }
    }

    private fun setImageResUrl(imageSrc: String?) {
        viewModel.state.stateLiveData?.postValue(State.loading(""))
        imageSrc?.let {
            var mUrl = getUrl(imageSrc)
            if (!mUrl.contains("http")) {
                mUrl = "file://$mUrl"
            }
            Picasso.get()
                .load(mUrl)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(
                    getDataBindingView<FragmentViewDocumentBinding>().ivImage,
                    object : Callback {
                        override fun onSuccess() {
                            if (isAdded) {
                                viewModel.state.stateLiveData?.postValue(State.success(""))
                            }
                        }

                        override fun onError(e: java.lang.Exception?) {
                            if (isAdded) {
                                viewModel.state.stateLiveData?.postValue(
                                    State.error(
                                        getString(
                                            Strings.screen_view_document_refresh_text_description
                                        )
                                    )
                                )
                            }
                        }
                    })
        }
    }

    private val refreshViewWithUpdatedData =
        Observer<Document?> {
            it?.let {
                viewModel.state.fileUrl.value = it.fileURL
                viewModel.state.fileExtension.value = it.extension
                loadFileInView()
            }
            viewModel.state.isNeedToShowUpdateDialogue.value = false
        }

    private val isEditAbleCheck =
        Observer<Boolean?> {
            if (it == false) {
                viewModel.state.isDeleteAble.value = false
                viewModel.state.isUpdateAble.value = false
            }
        }

    private val isPdfViewNeedToActive =
        Observer<String?> {
            it?.let { extension ->
                viewModel.state.isPDF.value = extension.contains("pdf")
            }
        }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(listener)
        viewModel.state.stateLiveData?.removeObservers(this)
        viewModel.state.fileDataFromRefreshApi.removeObservers(this)
        viewModel.state.isEditable.removeObservers(this)
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, listener)
        viewModel.state.fileDataFromRefreshApi.observe(this, refreshViewWithUpdatedData)
        viewModel.state.isEditable.observe(this, isEditAbleCheck)
        viewModel.state.fileExtension.observe(this, isPdfViewNeedToActive)
    }

    fun refreshViewNeedTOShow(isVisible: Boolean) {
        if (isVisible) {
            viewModel.state.stateLiveData?.postValue(State.error(getString(Strings.screen_view_document_refresh_text_description)))
        } else {
            viewModel.state.stateLiveData?.postValue(State.success(""))
        }
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

    fun isDeleteAble(isDeleteAble: Boolean) {
        if (isDeleteAble) {
            viewModel.state.isUpdateAble.value = false
            viewModel.state.isDeleteAble.value = true
        } else {
            viewModel.state.isUpdateAble.value = true
            viewModel.state.isDeleteAble.value = false
        }
    }

    fun performDelete() {
        isDeleteAble(false)
        viewModel.state.fileUrl.value = ""
        viewModel.state.fileExtension.value = ""
        viewModel.state.documentType.value = ""
        viewModel.fileForUpdate = null
        getDataBindingView<FragmentViewDocumentBinding>().ivImage.setImageResource(0)
    }

    fun onBackPressedWithData() {
        val intent = Intent()
        if (viewModel.state.isUpdateAble.value == false) {
            viewModel.fileForUpdate?.let { file ->
                intent.putExtra(
                    ExtraKeys.FILE_FOR_UPDATE.name,
                    file
                )
            }
            activity?.setResult(Activity.RESULT_OK, intent)
        } else {
            intent.putExtra(ExtraKeys.DELETED.name, true)
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

}
