package co.yap.modules.kyc.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityEidInfoReviewBinding
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.enums.KYCAction
import co.yap.modules.kyc.viewmodels.EidInfoReviewViewModel
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.translation.Strings
import co.yap.widgets.Status
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.widgets.edittext.EditTextRichDrawable
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.DateUtils.DEFAULT_DATE_FORMAT
import co.yap.yapcore.helpers.DateUtils.TIME_ZONE_Default
import co.yap.yapcore.helpers.DateUtils.dateToString
import co.yap.yapcore.helpers.Utils.hideKeyboard
import co.yap.yapcore.helpers.extentions.launchSheet
import co.yap.yapcore.helpers.showAlertDialogAndExitApp
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import com.digitify.identityscanner.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.docscanner.enums.DocumentType
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_eid_info_review.*
import java.io.File
import java.util.*


class EidInfoReviewFragment : KYCChildFragment<IEidInfoReview.ViewModel>(), IEidInfoReview.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_eid_info_review

    override val viewModel: EidInfoReviewViewModel
        get() = ViewModelProvider(this).get(EidInfoReviewViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataBindingView<ActivityEidInfoReviewBinding>().lifecycleOwner = this
        viewModel.validator = Validator(getDataBindingView<ActivityEidInfoReviewBinding>())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.parentViewModel?.skipFirstScreen?.value == true) {
            openCardScanner()
            tbBtnBack.setOnClickListener {
                viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
            }
        }
        addObservers()
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tvEidNumber -> {
                    disableEndDrawable(tvEidNumber)
                    manageFocus(tvEidNumber)
                }
                R.id.tvFirstName -> {
                    disableEndDrawable(tvFirstName)
                    manageFocus(tvFirstName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "first_name")
                    )
                }

                R.id.tvMiddleName -> {
                    disableEndDrawable(tvMiddleName)
                    manageFocus(tvMiddleName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "middle_name")
                    )
                }

                R.id.tvLastName -> {
                    disableEndDrawable(tvLastName)
                    manageFocus(tvLastName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "last_name")
                    )
                }

                R.id.tvNationality -> {
                    disableEndDrawable(tvNationality)
                    manageFocus(tvNationality)
                }

                R.id.tvDOB -> {
                    disableEndDrawable(tvDOB)
                    showDateOfBirthPicker(viewModel.state.dobCalendar)
                }

                R.id.tvGender -> {
                    disableEndDrawable(tvGender)
                    requireActivity().launchSheet(
                        itemClickListener = genderItemListener,
                        itemsList = viewModel.getGenderOptions(),
                        heading = getString(Strings.screen_b2c_eid_info_review_display_text_select_gender)
                    )
                }

                R.id.tvExpiryDate -> {
                    disableEndDrawable(tvExpiryDate)
                    showExpiryDatePicker(viewModel.state.expiryCalendar)
                }

                viewModel.eventErrorInvalidEid -> showInvalidEidScreen()
                viewModel.eventErrorExpiredEid -> showExpiredEidScreen()
                viewModel.eventErrorUnderAge -> showUnderAgeScreen()
                viewModel.eventErrorFromUsa -> showUSACitizenScreen()
                viewModel.eventRescan -> openCardScanner()
                R.id.tvNoThanks -> {
                    trackEventWithScreenName(FirebaseEvent.RESCAN_ID)
                    hideKeyboard(tvNoThanks)
                    openCardScanner()
                }
                viewModel.eventAlreadyUsedEid -> {
                    viewModel.parentViewModel?.finishKyc?.value =
                        DocumentsResponse(false, KYCAction.ACTION_EID_FAILED.name)
                }

                viewModel.eventNextWithError -> {
                    viewModel.performUploadDocumentsRequest(true) {
                        if (it.equals("success", true)) {
                            val action =
                                EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                                    viewModel.errorTitle, viewModel.errorBody
                                )
                            findNavController().navigate(action)
                        } else {
                            viewModel.state.toast = "${it}^${AlertType.DIALOG.name}"
                        }
                    }

                }
                viewModel.eventFinish -> {
                    viewModel.parentViewModel?.finishKyc?.value =
                        DocumentsResponse(false, KYCAction.ACTION_EID_FAILED.name)
                }
                viewModel.eventNext -> {
                    trackEventWithScreenName(FirebaseEvent.CONFIRM_ID)
//                    requireActivity().firebaseTagManagerEvent(FirebaseTagManagerModel(action = FirebaseEvents.CONFIRM_ID.event))
                    SessionManager.getAccountInfo()
                    SessionManager.onAccountInfoSuccess.observe(
                        viewLifecycleOwner,
                        Observer { isSuccess ->
                            if (isSuccess) {
                                viewModel.parentViewModel?.finishKyc?.value =
                                    DocumentsResponse(true)
                            } else {
                                showToast("Accounts info failed")
                                viewModel.parentViewModel?.finishKyc?.value =
                                    DocumentsResponse(true)
                            }

                        })
                }
                viewModel.eventEidUpdate -> {
                    SessionManager.getAccountInfo()
                    SessionManager.onAccountInfoSuccess.observe(
                        viewLifecycleOwner,
                        Observer { isSuccess ->
                            if (viewModel.parentViewModel?.amendmentMap != null) {
                                navigateToAmendmentSuccess()
                            }
                            if (isSuccess) {
                                viewModel.parentViewModel?.finishKyc?.value =
                                    DocumentsResponse(false, KYCAction.ACTION_EID_UPDATE.name)
                            } else {
                                showToast("Accounts info failed")
                                viewModel.parentViewModel?.finishKyc?.value =
                                    DocumentsResponse(false, KYCAction.ACTION_EID_UPDATE.name)
                            }

                        })
                }
                viewModel.eventCitizenNumberIssue, viewModel.eventEidExpiryDateIssue -> invalidCitizenNumber(
                    "Sorry, that didn’t work. Please try again"
                )
            }
        })
        viewModel.eidStateLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.ERROR) {
                invalidCitizenNumber(it.message ?: "Sorry, that didn’t work. Please try again")
            }
        })
    }

    private fun navigateToAmendmentSuccess() {
        val bundle = Bundle()
        bundle.putString(
            Constants.CONFIRMATION_DESCRIPTION,
            getString(R.string.common_display_text_y2y_general_share)
        )
        bundle.putSerializable(Constants.KYC_AMENDMENT_MAP, viewModel.parentViewModel?.amendmentMap)
        navigate(
            R.id.action_eidInfoReviewFragment_to_missingInfoConfirmationFragment,
            bundle
        )
    }

    private fun disableEndDrawable(view: EditTextRichDrawable?) {
        val list = listOf<EditTextRichDrawable>(
            tvEidNumber,
            tvFirstName,
            tvMiddleName,
            tvLastName,
            tvNationality,
            tvDOB,
            tvGender,
            tvExpiryDate
        )
        list.forEach {
            it.setDrawableEndVectorId(
                if (view?.id == it.id) R.drawable.ic_edit_disable else R.drawable.ic_edit
            )
        }
    }

    private fun showDateOfBirthPicker(calendar: Calendar) {
        val dpd =
            DatePickerDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                viewModel.state.dateOfBirth = dateToString(
                    calendar.time, DEFAULT_DATE_FORMAT,
                    TIME_ZONE_Default
                )
            }, calendar)
        dpd.maxDate = Calendar.getInstance()
        dpd.version = DatePickerDialog.Version.VERSION_2
        childFragmentManager.run {
            dpd.accentColor = requireContext().getColor(R.color.colorPrimary)
            dpd.show(this, "")
        }
    }

    private fun showExpiryDatePicker(calendar: Calendar) {
        val dpd =
            DatePickerDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                viewModel.state.expiryDate = dateToString(
                    calendar.time, DEFAULT_DATE_FORMAT,
                    TIME_ZONE_Default
                )
            }, calendar)
        dpd.minDate = Calendar.getInstance()
        dpd.version = DatePickerDialog.Version.VERSION_2
        childFragmentManager.run {
            dpd.accentColor = requireContext().getColor(R.color.colorPrimary)
            dpd.show(this, "")
        }
    }

    private fun invalidCitizenNumber(title: String) {
        activity?.let {
            it.showAlertDialogAndExitApp(
                message = title,
                callback = {
                    openCardScanner()
                },
                closeActivity = false
            )
            viewModel.parentViewModel?.paths?.forEach { filePath ->
                File(filePath).deleteRecursively()
            }
        }
    }

    private fun manageFocus(
        editText: EditText
    ) {
        if (!editText.isFocused) {
            editText.isFocusable = true

            editText.isFocusableInTouchMode = true
            editText.isActivated = true
            editText.setSelection(editText.length())
            editText.requestFocus()
            editText.performClick()
            (editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                disableEndDrawable(null)
            }
        }

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE
            ) {
                disableEndDrawable(null)
            }
            false
        }

    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    override fun showUnderAgeScreen() {
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    override fun showExpiredEidScreen() {
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    override fun showInvalidEidScreen() {
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    private fun showEIDAlert(
        message: String,
        posBtn: String,
        negBtn: String? = null,
        response: (Boolean) -> Unit
    ) {
        AlertDialog.Builder(requireContext()).apply {
            setCancelable(false)
            setMessage(message)
            setPositiveButton(posBtn) { _, _ ->
                response.invoke(true)
            }
            if (negBtn != null)
                setNegativeButton(negBtn) { _, _ ->
                    response.invoke(false)
                }
        }.create().show()
    }

    override fun showUSACitizenScreen() {
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null && viewModel.parentViewModel?.skipFirstScreen?.value == true) {

        }
        if (requestCode == IdentityScannerActivity.SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
            data?.let {
                viewModel.onEIDScanningComplete(it.getParcelableExtra(IdentityScannerActivity.SCAN_RESULT))
            }
        } else {
            viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
        }
    }

    override fun openCardScanner() {
        viewModel.invalidateFields()
        startActivityForResult(
            IdentityScannerActivity.getLaunchIntent(
                requireContext(),
                DocumentType.EID,
                IdentityScannerActivity.SCAN_FROM_CAMERA
            ),
            IdentityScannerActivity.SCAN_EID_CAM
        )
    }

    override fun onDestroy() {
        viewModel.parentViewModel?.paths?.forEach { filePath ->
            File(filePath).deleteRecursively()
        }
        super.onDestroy()
    }

    private val genderItemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
                viewModel.state.gender = (data as BottomSheetItem).tag ?: ""
        }
    }
}