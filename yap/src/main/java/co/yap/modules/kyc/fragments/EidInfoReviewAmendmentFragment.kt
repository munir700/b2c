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
import androidx.navigation.NavDirections
import co.yap.BR
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.databinding.FragmentEidInfoReviewAmendmentBinding
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.enums.KYCAction
import co.yap.modules.kyc.viewmodels.EidInfoReviewAmendmentViewModel
import co.yap.modules.onboarding.enums.EidInfoEvents
import co.yap.modules.onboarding.interfaces.IEidInfoReviewAmendment
import co.yap.translation.Strings
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.widgets.edittext.EditTextRichDrawable
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.constants.RequestCodes.REQUEST_UQUDO
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.SystemConfigurations
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.*
import co.yap.yapcore.helpers.extentions.deleteTempFolder
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.helpers.extentions.launchBottomSheet
import co.yap.yapcore.helpers.extentions.launchSheet
import co.yap.yapcore.helpers.extentions.parseToInt
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*


class EidInfoReviewAmendmentFragment :
    KYCChildFragment<FragmentEidInfoReviewAmendmentBinding, IEidInfoReviewAmendment.ViewModel>(),
    IEidInfoReviewAmendment.View, View.OnFocusChangeListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_eid_info_review_amendment

    override val viewModel: EidInfoReviewAmendmentViewModel
        get() = ViewModelProvider(this).get(EidInfoReviewAmendmentViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.validator?.targetViewBinding = viewDataBinding
        viewModel.validator?.toValidate()
        addObservers()
        // TODO use MaskTextWatcher to mask the eid number
        viewDataBinding.tvEidNumber.filters =
            arrayOf(
                InputFilter.LengthFilter(resources.getInteger(R.integer.eid_length)),
                EidFilter(intArrayOf(3, 8, 16), '-')
            )

        viewModel.parentViewModel?.uqudoManager?.getPayloadData()?.let { identity ->
            viewModel.populateUqudoState(identity = identity)
        } ?: viewModel.requestAllAPIs(true)
        viewModel.state.eidExpireLimitDays.value =
            SessionManager.systemConfiguration.value?.get(SystemConfigurations.EID_EXPIRE_LIMIT_DAYS.key)?.value?.parseToInt()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.parentViewModel?.skipFirstScreen?.value == true) {
            viewModel.state.errorScreenVisited = false
            viewDataBinding.tbBtnBack.setOnClickListener {
                viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
            }
        }
    }

    private fun addObservers() {
        with(viewModel) {
            if (parentViewModel?.skipFirstScreen?.value == true) {
                state.errorScreenVisited = false
                viewDataBinding.tbBtnBack.setOnClickListener {
                    parentViewModel?.finishKyc?.value = DocumentsResponse(false)
                }
            }
            addFocusListeners()
            clickEvent.observe(viewLifecycleOwner, clickEventObserver)
            state.dateOfBirth.observe(viewLifecycleOwner, Observer {
                viewModel.handleAgeValidation()
            })
            state.nationality.observe(viewLifecycleOwner, Observer {
                viewModel.handleIsUsValidation()
            })

            eidStateLiveData.observe(viewLifecycleOwner, Observer
            {
                handleState(it)
            })
            parentViewModel?.uqudoManager?.getUqudoAccessToken()
                ?.observe(viewLifecycleOwner, Observer { response ->
                    if (viewModel.parentViewModel?.uqudoManager?.getPayloadData() == null) {
                        if (response.accessToken.isNullOrEmpty().not()
                        ) viewModel.eidStateLiveData.postValue(State.empty("")) else viewModel.eidStateLiveData.postValue(
                            State.error("Sorry, that didn’t work. Please try again")
                        )
                    }
                })
            state.eidImageDownloaded.observe(viewLifecycleOwner, ::handleDownLoadState)
        }

    }

    private fun handleDownLoadState(state: State?) {
        when (state?.status) {
            Status.LOADING -> viewModel.state.loading = true
            Status.EMPTY -> {
                viewModel.state.loading = false
                requireContext().alert(
                    message = state.message ?: "",
                    cancelable = false,
                    positiveButton = getString(R.string.screen_b2c_eid_info_review__button_title_rescan_eid)
                ) {
                    initializeUqudoScanner()
                }

            }
            Status.ERROR -> {
                viewModel.state.loading = false
                requireContext().alert(
                    message = state.message ?: "",
                    cancelable = false,
                    positiveButton = getString(R.string.common_display_text_retry)
                ) {
                    viewModel.downloadImageInBackground()
                }
            }
            else ->
                viewModel.state.loading = false

        }
    }

    private fun navigateToConfirmNameFragment() {
        viewModel.parentViewModel?.state?.let { state ->
            state.identityNo.set(viewModel.state.citizenNumber.value?.replace("-", ""))
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
            viewDataBinding.tvEidNumber,
            viewDataBinding.tvFirstName,
            viewDataBinding.tvMiddleName,
            viewDataBinding.tvLastName
        )
        list.forEach {
            it.setDrawableEndVectorId(
                if (view?.id == it.id) R.drawable.ic_edit_disable else R.drawable.ic_edit
            )
        }
    }

    private fun showDatePicker(
        calendar: Calendar,
        id: Int? = R.id.tvExpiryDate,
        picked: (date: String) -> Unit
    ) {
        val dpd =
            DatePickerDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                picked.invoke(
                    DateUtils.dateToString(
                        calendar.time, DateUtils.DEFAULT_DATE_FORMAT,
                        DateUtils.TIME_ZONE_Default
                    )
                )
            }, calendar)
        if (id == R.id.tvDOB) dpd.maxDate = Calendar.getInstance() else dpd.minDate =
            Calendar.getInstance()
        dpd.version = DatePickerDialog.Version.VERSION_2
        childFragmentManager.run {
            dpd.accentColor = requireContext().getColor(R.color.colorPrimary)
            dpd.show(this, "")
        }
    }

    private fun invalidCitizenNumber(title: String, event: () -> Unit) {
        activity?.let {
            it.showAlertDialogAndExitApp(
                message = title,
                callback = {
                    event.invoke()
                },
                closeActivity = false
            )
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
        removeObserver()
        super.onDestroyView()
    }

    override fun showErrorScreen(actionId: NavDirections) {
        viewModel.state.errorScreenVisited = true
        navigate(actionId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_UQUDO -> {
                val uqudoJWT = data?.getStringExtra("data")
                if (uqudoJWT.isNullOrBlank().not()) {
                    viewModel.parentViewModel?.uqudoManager?.decodeEncodedUqudoToken(
                        uqudoJWT ?: ""
                    ) {
                        viewModel.getKYCDataFromServer()
                    }
                } else {
                    if (viewModel.parentViewModel?.uqudoManager?.getPayloadData() == null) requireActivity().finish()
                }
            }
            else -> viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)

        }
    }

    override fun onBackPressed(): Boolean {
        if (viewModel.state.errorScreenVisited) {
            requireActivity().finish()
        }
        return super.onBackPressed()
    }

    private fun addFocusListeners() {
        viewDataBinding.tvEidNumber.onFocusChangeListener = this
        viewDataBinding.tvFirstName.onFocusChangeListener = this
        viewDataBinding.tvMiddleName.onFocusChangeListener = this
        viewDataBinding.tvLastName.onFocusChangeListener = this
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

    private fun handleState(state: State?) {
        when (state?.status) {
            Status.IDEAL -> {
                //do nothing
            }
            Status.LOADING -> {
                viewDataBinding.multiStateView.viewState = MultiStateView.ViewState.LOADING
            }
            Status.EMPTY -> {
                viewDataBinding.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                initializeUqudoScanner()
            }
            Status.SUCCESS -> {
                viewDataBinding.multiStateView.viewState = MultiStateView.ViewState.CONTENT
                viewModel.parentViewModel?.uqudoManager?.getPayloadData()?.let { identity ->
                    viewModel.populateUqudoState(identity = identity)
                }
            }
            Status.ERROR -> {
                viewDataBinding.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                invalidCitizenNumber(state.message ?: "Sorry, that didn’t work. Please try again") {
                    if (viewModel.parentViewModel?.comingFrom?.value.isNullOrBlank()
                            .not()
                    ) navigateBack() else requireActivity().finish()
                }
            }
            else -> {
                throw IllegalStateException("State is not handled " + state?.status)
            }
        }
    }

    private fun initializeUqudoScanner() {
        with(viewModel.parentViewModel?.uqudoManager) {
            if (this?.isAccessTokenExpired() == true) viewModel.requestAllAPIs(false)
            else this?.initiateUqudoScanning().apply {
                startActivityForResult(this, RequestCodes.REQUEST_UQUDO)
            }
        }
    }

    private val genderItemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.state.gender = (data as BottomSheetItem).tag ?: ""
        }
    }
    private val selectCountryItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.state.nationality.value = (data as Country)
        }
    }
    private val clickEventObserver = Observer<Int> { id ->
        with(viewModel) {
            when (id) {
                R.id.tvEidNumber -> {
                    disableEndDrawable(viewDataBinding.tvEidNumber)
                    manageFocus(viewDataBinding.tvEidNumber)
                }
                R.id.tvFirstName -> {
                    disableEndDrawable(viewDataBinding.tvFirstName)
                    manageFocus(viewDataBinding.tvFirstName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "first_name")
                    )
                }

                R.id.tvMiddleName -> {
                    disableEndDrawable(viewDataBinding.tvMiddleName)
                    manageFocus(viewDataBinding.tvMiddleName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "middle_name")
                    )
                }

                R.id.tvLastName -> {
                    disableEndDrawable(viewDataBinding.tvLastName)
                    manageFocus(viewDataBinding.tvLastName)
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
                        countriesList = countries
                    )
                }

                R.id.tvDOB -> {
                    showDatePicker(state.dobCalendar, id) {
                        viewModel.state.dateOfBirth.value = it
                    }
                }

                R.id.tvGender -> {
                    requireActivity().launchSheet(
                        itemClickListener = genderItemListener,
                        itemsList = getGenderOptions(),
                        heading = getString(Strings.screen_b2c_eid_info_review_display_text_select_gender)
                    )
                }

                R.id.tvExpiryDate -> {
                    showDatePicker(state.expiryCalendar) {
                        state.expiryDate = it
                    }
                }

                EidInfoEvents.EVENT_ERROR_INVALID_EID.eventId -> showErrorScreen(
                    EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                        viewModel.errorTitle, viewModel.errorBody
                    )
                )
                EidInfoEvents.EVENT_ERROR_EXPIRED_EID.eventId -> showErrorScreen(
                    EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                        viewModel.errorTitle, viewModel.errorBody
                    )
                )
                EidInfoEvents.EVENT_ERROE_UNDERAGE.eventId -> showErrorScreen(
                    EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                        viewModel.errorTitle, viewModel.errorBody
                    )
                )
                EidInfoEvents.EVENT_ERROR_FROM_USA.eventId -> showErrorScreen(
                    EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                        viewModel.errorTitle, viewModel.errorBody
                    )
                )
                EidInfoEvents.EVENT_RESCAN.eventId -> {
                    initializeUqudoScanner()
                }
                R.id.tvNoThanks -> {
                    trackEventWithScreenName(FirebaseEvent.RESCAN_ID)
                    viewDataBinding.tvNoThanks.hideKeyboard()
                    initializeUqudoScanner()
                }
                EidInfoEvents.EVENT_ALREADY_USED_EID.eventId -> {
                    parentViewModel?.finishKyc?.value =
                        DocumentsResponse(false, KYCAction.ACTION_EID_FAILED.name)
                }

                EidInfoEvents.EVENT_NEXT_WITH_ERROR.eventId -> {
                    performUqudoUploadDocumentsRequest(true) {
                        if (it.equals("success", true)) {
                            showErrorScreen(
                                EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                                    errorTitle, errorBody
                                )
                            )
                        } else {
                            state.toast = "${it}^${AlertType.DIALOG.name}"
                        }
                    }

                }
                EidInfoEvents.EVENT_FINISH.eventId -> {
                    parentViewModel?.finishKyc?.value =
                        DocumentsResponse(false, KYCAction.ACTION_EID_FAILED.name)
                }
                EidInfoEvents.EVENT_NEXT.eventId -> {
                    trackEventWithScreenName(FirebaseEvent.CONFIRM_ID)
                    requireContext().deleteTempFolder()
                    SessionManager.getAccountInfo()
                    SessionManager.onAccountInfoSuccess.observe(
                        viewLifecycleOwner,
                        Observer { isSuccess ->
                            if (isSuccess) {
                                if (isFromAmendment()) {
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
                EidInfoEvents.EVENT_EID_UPDATE.eventId -> {
                    SessionManager.getAccountInfo()
                    SessionManager.onAccountInfoSuccess.observe(
                        viewLifecycleOwner,
                        Observer { isSuccess ->
                            if (isSuccess) {
                                if (isFromAmendment()) {
                                    navigateToAmendmentSuccess()
                                } else {
                                    parentViewModel?.finishKyc?.value =
                                        DocumentsResponse(false, KYCAction.ACTION_EID_UPDATE.name)
                                }
                            } else {
                                showToast("Accounts info failed")
                                parentViewModel?.finishKyc?.value =
                                    DocumentsResponse(false, KYCAction.ACTION_EID_UPDATE.name)
                            }

                        })
                }
                EidInfoEvents.EVENT_CITIZEN_NUMBER_ISSUE.eventId, EidInfoEvents.EVENT_EID_EXPIRY_DATE_ISSUE.eventId -> invalidCitizenNumber(
                    "Sorry, that didn’t work. Please try again"
                ) {
                    initializeUqudoScanner()
                }
                EidInfoEvents.EVENT_EID_ABOUT_TO_EXPIRY_DATE_ISSUE.eventId -> showAboutToExpireDialogue()
            }
        }
    }

    fun removeObserver() {
        viewModel.clickEvent.removeObserver(clickEventObserver)
        viewModel.eidStateLiveData.removeObservers(this)
        viewModel.uqudoResponse.removeObservers(this)
    }

    private fun showAboutToExpireDialogue() {
        context?.let { it ->
            it.customAlertDialog(
                title = getString(R.string.expiry_dialogue_title_oops),
                message = getString(R.string.expiry_dialogue_message),
                positiveButton = getString(R.string.common_text_ok),
                cancelable = false,
                positiveCallback = {
                   navigateBack()
                }
            )
        }
    }
}