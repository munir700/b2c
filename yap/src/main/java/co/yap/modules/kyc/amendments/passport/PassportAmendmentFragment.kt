package co.yap.modules.kyc.amendments.passport

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPassportAmendmentBinding
import co.yap.modules.dashboard.addionalinfo.fragments.AdditionalInfoScanDocumentFragment
import co.yap.modules.dashboard.addionalinfo.model.AdditionalDocumentImage
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.launchSheet
import co.yap.yapcore.helpers.extentions.openAppSetting
import co.yap.yapcore.helpers.extentions.openFilePicker
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.interfaces.OnItemClickListener
import java.io.File

class PassportAmendmentFragment : BaseBindingFragment<IPassportAmendment.ViewModel>(),
    IPassportAmendment.View {
    private var permissionHelper: PermissionHelper? = null
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_passport_amendment

    override val viewModel: IPassportAmendment.ViewModel
        get() = ViewModelProvider(this).get(PassportAmendmentVM::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionHelper = PermissionHelper(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 600
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataBindingView<FragmentPassportAmendmentBinding>().lifecycleOwner = this
        viewModel.clickEvent.observe(viewLifecycleOwner, onViewClickObserver)
    }

    private val onViewClickObserver = Observer<Int> {
        when (it) {
            R.id.etIssueDate -> {
                viewModel.getDatePicker(viewModel.state.issueDataCalender) { calender ->
                    viewModel.state.issueDataCalender = calender
                }.run {
                    this.accentColor = requireContext().getColor(R.color.colorPrimary)
                    this.show(childFragmentManager, "")
                }
            }
            R.id.etExpireDate -> {
                viewModel.getDatePicker(viewModel.state.issueDataCalender) { calender ->
                    viewModel.state.expireDataCalender = calender
                }.run {
                    this.accentColor = requireContext().getColor(R.color.colorPrimary)
                    this.show(childFragmentManager, "")
                }

            }
            R.id.btnNext -> {

            }
            R.id.cvCard -> {
                askPermission("Passport")
            }
        }
    }

    private fun openBottomSheet(name: String?) {
        childFragmentManager.run {
            requireActivity().launchSheet(
                itemClickListener = itemListener,
                itemsList = viewModel.getUploadDocumentOptions(false),
                heading = name + " " + getString(Strings.common_display_text_copy),
                subHeading = getString(Strings.screen_additional_info_label_text_bottom_sheet_des) + " " + name
            )
        }
    }

    private val itemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when ((data as BottomSheetItem).tag) {
                PhotoSelectionType.CAMERA.name -> {
                    openScanDocumentFragment(getString(R.string.scan_passport))
                }

                PhotoSelectionType.GALLERY.name -> {
                    requireActivity().openFilePicker("File picker",
                        completionHandler = { _, dataUri ->
                            dataUri?.let { uriIntent ->
//                                uploadDocumentAndMoveNext(
//                                    FileUtils.getFile(requireContext(), uriIntent.data),
//                                    currentPos ?: 0,
//                                    currentDocument ?: AdditionalDocument(),
//                                    isFromCamera = false,
//                                    contentType = requireContext().contentResolver.getType(
//                                        uriIntent.data ?: Uri.EMPTY
//                                    )
//                                )
                            }
                        })
                }

                PhotoSelectionType.REMOVE_PHOTO.name -> {

                }

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
//                    uploadDocumentAndMoveNext(
//                        file,
//                        currentPos ?: 0,
//                        currentDocument ?: AdditionalDocument(), isFromCamera = true,
//                        contentType = null
//                    )
                }
            }
        }
    }

    private fun askPermission(data: String? = "") {
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                openBottomSheet(data)
            }

            override fun onPermissionDenied() {
                super.onPermissionDenied()
            }

            override fun onPermissionDeniedBySystem() {
                confirm(
                    getString(R.string.permissions_rationale_ask_again),
                    getString(R.string.title_settings_dialog)
                ) {
                    openAppSetting(200)

                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(
            requestCode,
            permissions as Array<String>,
            grantResults
        )

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}