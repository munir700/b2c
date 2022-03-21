package co.yap.modules.kyc.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.databinding.FragmentEidInfoReviewAmendmentBinding
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.enums.KYCAction
import co.yap.modules.kyc.viewmodels.EidInfoReviewAmendmentViewModel
import co.yap.modules.onboarding.interfaces.IEidInfoReviewAmendment
import co.yap.translation.Strings
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.widgets.edittext.EditTextRichDrawable
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.*
import co.yap.yapcore.helpers.extentions.launchBottomSheet
import co.yap.yapcore.helpers.extentions.launchSheet
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog

import java.io.File
import java.util.*

class EidInfoReviewAmendmentFragment : KYCChildFragment<IEidInfoReviewAmendment.ViewModel>(),
    IEidInfoReviewAmendment.View, View.OnFocusChangeListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_eid_info_review_amendment

    override val viewModel: EidInfoReviewAmendmentViewModel
        get() = ViewModelProvider(this).get(EidInfoReviewAmendmentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().lifecycleOwner = this
        viewModel.validator?.targetViewBinding = getViewBinding()
        viewModel.validator?.toValidate()
        // TODO use MaskTextWatcher to mask the eid number
        getViewBinding().tvEidNumber.filters =
            arrayOf(
                InputFilter.LengthFilter(resources.getInteger(R.integer.eid_length)),
                EidFilter(intArrayOf(3, 8, 16), '-')
            )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.parentViewModel?.skipFirstScreen?.value == true) {
            if (!viewModel.state.errorScreenVisited) {
                //TODO Uqudo Camera WILL BE Handled here
                showToast("Uqudo Camera will be integrated here!!")
                //  openCardScanner()
            }
            viewModel.state.errorScreenVisited = false
            getViewBinding().tbBtnBack.setOnClickListener {
                viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
            }
        }
        addFocusListeners()
        addObservers()
    }

    private fun addFocusListeners() {
        getViewBinding().tvEidNumber.onFocusChangeListener =
            this
        getViewBinding().tvFirstName.onFocusChangeListener =
            this
        getViewBinding().tvMiddleName.onFocusChangeListener =
            this
        getViewBinding().tvLastName.onFocusChangeListener =
            this
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tvEidNumber -> {
                    disableEndDrawable(getViewBinding().tvEidNumber)
                    manageFocus(getViewBinding().tvEidNumber)
                }
                R.id.tvFirstName -> {
                    disableEndDrawable(getViewBinding().tvFirstName)
                    manageFocus(getViewBinding().tvFirstName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "first_name")
                    )
                }

                R.id.tvMiddleName -> {
                    disableEndDrawable(getViewBinding().tvMiddleName)
                    manageFocus(getViewBinding().tvMiddleName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "middle_name")
                    )
                }

                R.id.tvLastName -> {
                    disableEndDrawable(getViewBinding().tvLastName)
                    manageFocus(getViewBinding().tvLastName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "last_name")
                    )
                }

                R.id.tvNationality -> {
                    launchBottomSheet(
                        itemClickListener = selectCountryItemClickListener,
                        label = getString(Strings.screen_place_of_birth_display_text_select_country),
                        viewType = Constants.VIEW_WITH_FLAG,
                        countriesList = viewModel.countries
                    )
                }

                R.id.tvDOB -> {
                    showDateOfBirthPicker(viewModel.state.dobCalendar)
                }

                R.id.tvGender -> {
                    requireActivity().launchSheet(
                        itemClickListener = genderItemListener,
                        itemsList = viewModel.getGenderOptions(),
                        heading = getString(Strings.screen_b2c_eid_info_review_display_text_select_gender)
                    )
                }

                R.id.tvExpiryDate -> {
                    showExpiryDatePicker(viewModel.state.expiryCalendar)
                }

                viewModel.eventErrorInvalidEid -> showInvalidEidScreen()
                viewModel.eventErrorExpiredEid -> showExpiredEidScreen()
                viewModel.eventErrorUnderAge -> showUnderAgeScreen()
                viewModel.eventErrorFromUsa -> showUSACitizenScreen()
                viewModel.eventRescan -> {  //TODO Uqudo Camera WILL BE Handled here
                    showToast("Uqudo Camera will be integrated here!!")
                }// openCardScanner()
                R.id.tvNoThanks -> {
                    trackEventWithScreenName(FirebaseEvent.RESCAN_ID)
                    Utils.hideKeyboard(getViewBinding().tvNoThanks)
                    //TODO Uqudo Camera WILL BE Handled here
                    showToast("Uqudo Camera will be integrated here!!")
                    // openCardScanner()
                }
                viewModel.eventAlreadyUsedEid -> {
                    viewModel.parentViewModel?.finishKyc?.value =
                        DocumentsResponse(false, KYCAction.ACTION_EID_FAILED.name)
                }

                viewModel.eventNextWithError -> {
                    viewModel.performUploadDocumentsRequest(true) {
                        if (it.equals("success", true)) {
                            viewModel.state.errorScreenVisited = true
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
                                if (viewModel.isFromAmendment()) {
                                    navigateToAmendmentSuccess()
                                } else {
                                    navigateToConfirmNameFragment()
                                }
                            } else {
                                showToast("Accounts info failed")
                                navigateToConfirmNameFragment()
                            }

                        })
                }
                viewModel.eventEidUpdate -> {
                    SessionManager.getAccountInfo()
                    SessionManager.onAccountInfoSuccess.observe(
                        viewLifecycleOwner,
                        Observer { isSuccess ->
                            if (isSuccess) {
                                if (viewModel.isFromAmendment()) {
                                    navigateToAmendmentSuccess()
                                } else {
                                    viewModel.parentViewModel?.finishKyc?.value =
                                        DocumentsResponse(false, KYCAction.ACTION_EID_UPDATE.name)
                                }
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
        viewModel.state.dateOfBirth.observe(viewLifecycleOwner, Observer {
            viewModel.handleAgeValidation()
        })
        viewModel.state.nationality.observe(viewLifecycleOwner, Observer {
            viewModel.handleIsUsValidation()
        })

        viewModel.eidStateLiveData.observe(viewLifecycleOwner, Observer
        {
            handleState(it)
        })
        viewModel.uqudoToken.observe(viewLifecycleOwner, Observer { token ->
            //TODO Uqudo will be initialised here
            if (token.isNullOrEmpty()
                    .not() && viewModel.state.isTokenValid.get()
            ) viewModel.eidStateLiveData.postValue(State.empty("")) else viewModel.eidStateLiveData.postValue(
                State.error("")
            )
        })
    }

    private fun navigateToConfirmNameFragment() {
        viewModel.parentViewModel?.state?.let { state ->
            state.identityNo.set(viewModel.state.citizenNumber.replace("-", ""))
            state.middleName.set(viewModel.state.middleName)
            state.firstName.set(viewModel.state.firstName)
            state.lastName.set(viewModel.state.lastName)
            state.nationality.set(viewModel.state.nationality.value?.isoCountryCode2Digit)
            SharedPreferenceManager.getInstance(requireContext())
                .save(Constants.KYC_FIRST_NAME, state.firstName.get() ?: "")
            SharedPreferenceManager.getInstance(requireContext())
                .save(Constants.KYC_LAST_NAME, state.lastName.get() ?: "")
            SharedPreferenceManager.getInstance(requireContext())
                .save(Constants.KYC_MIDDLE_NAME, state.middleName.get() ?: "")
            navigateWithPopup(
                R.id.action_eidInfoReviewFragment_to_confirmCardNameFragment,
                R.id.eidInfoReviewFragment
            )
        }
    }

    private fun navigateToAmendmentSuccess() {
        viewModel.parentViewModel?.hideProgressToolbar?.value = true
        viewModel.parentViewModel?.amendmentMap?.let { amendmentMap ->
            val bundle = Bundle()
            bundle.putSerializable(
                Constants.CONFIRMATION_DESCRIPTION,
                Pair(
                    first = getString(if (amendmentMap.size == 1) R.string.screen_missing_info_confirmation_display_all_set_title else R.string.screen_missing_info_confirmation_display_step_step_completed_title),
                    second = getString(if (amendmentMap.size == 1) R.string.screen_missing_info_confirmation_display_all_set_description else R.string.screen_missing_info_confirmation_display_step_step_completed_description)
                )
            )
            bundle.putSerializable(
                Constants.KYC_AMENDMENT_MAP,
                viewModel.parentViewModel?.amendmentMap
            )
            navigate(
                R.id.action_eidInfoReviewAmendmentFragment_to_missingInfoConfirmationFragment,
                bundle
            )
        }
    }

    private fun disableEndDrawable(view: EditTextRichDrawable?) {
        val list = listOf(
            getViewBinding().tvEidNumber,
            getViewBinding().tvFirstName,
            getViewBinding().tvMiddleName,
            getViewBinding().tvLastName
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
                viewModel.state.dateOfBirth.value = DateUtils.dateToString(
                    calendar.time, DateUtils.DEFAULT_DATE_FORMAT,
                    DateUtils.TIME_ZONE_Default
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
                viewModel.state.expiryDate = DateUtils.dateToString(
                    calendar.time, DateUtils.DEFAULT_DATE_FORMAT,
                    DateUtils.TIME_ZONE_Default
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
                    //TODO Uqudo Camera WILL BE Handled here
                    showToast("Uqudo Camera will be integrated here!!")
                    // openCardScanner()
                },
                closeActivity = false
            )
            viewModel.parentViewModel?.paths?.forEach { filePath ->
                File(filePath).deleteRecursively()
            }
        }
    }

    private fun manageFocus(
        editText: EditTextRichDrawable
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
        viewModel.state.errorScreenVisited = true
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    override fun showExpiredEidScreen() {
        viewModel.state.errorScreenVisited = true
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    override fun showInvalidEidScreen() {
        viewModel.state.errorScreenVisited = true
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    override fun showUSACitizenScreen() {
        viewModel.state.errorScreenVisited = true
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*      if (data == null && viewModel.parentViewModel?.skipFirstScreen?.value == true) {
        //TODO Uqudo Camera WILL BE Handled here
        showToast("Uqudo Camera will be integrated here!!")
       /* if (data == null && viewModel.parentViewModel?.skipFirstScreen?.value == true) {

              }
              if (requestCode == IdentityScannerActivity.SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
                  data?.let {
                      it.getParcelableExtra<IdentityScannerResult>(IdentityScannerActivity.SCAN_RESULT)
                          ?.let { it1 ->
                              viewModel.onEIDScanningComplete(
                                  it1
                              )
                          }
                  }
              } else {
                  viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
              }*/
        }
        if (requestCode == IdentityScannerActivity.SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
            data?.let {
                it.getParcelableExtra<IdentityScannerResult>(IdentityScannerActivity.SCAN_RESULT)
                    ?.let { it1 ->
                        viewModel.onEIDScanningComplete(
                            it1
                        )
                    }
            }
        } else {
            viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
        }*/
    }
/*
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
    }*/

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

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            disableEndDrawable(null)
        } else {
            val editText = v as EditTextRichDrawable?
            disableEndDrawable(editText)
            editText?.setSelection(editText.length())
        }
    }

    private val selectCountryItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.state.nationality.value = (data as Country)
        }
    }

    private fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.LOADING
            }
            Status.EMPTY -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.EMPTY
                initializeUqudoScanner()
            }
            Status.SUCCESS -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.CONTENT
                viewModel.parentViewModel?.identity?.let {
                    viewModel.populateState(it)
                }
            }
            Status.ERROR -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.ERROR
                invalidCitizenNumber(state.message ?: "Sorry, that didn’t work. Please try again")
            }
            else -> {
                throw IllegalStateException("State is not handled " + state?.status)
            }
        }
    }

    private fun initializeUqudoScanner() {
        //TODO Initialise uqudo scanner here
    }

    private fun getViewBinding() = getDataBindingView<FragmentEidInfoReviewAmendmentBinding>()
}