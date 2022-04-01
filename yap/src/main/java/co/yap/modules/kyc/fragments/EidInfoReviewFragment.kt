package co.yap.modules.kyc.fragments

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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEidInfoReviewBinding
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.enums.KYCAction
import co.yap.modules.kyc.uqudo.UqudoScannerManager
import co.yap.modules.kyc.viewmodels.EidInfoReviewViewModel
import co.yap.modules.onboarding.enums.EidInfoEvents
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.networking.customers.responsedtos.UqudoPayLoad
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes.REQUEST_UQUDO
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils.hideKeyboard
import co.yap.yapcore.helpers.extentions.deleteTempFolder
import co.yap.yapcore.helpers.showAlertDialogAndExitApp
import co.yap.yapcore.managers.SessionManager
import java.io.File

class EidInfoReviewFragment : KYCChildFragment<IEidInfoReview.ViewModel>(), IEidInfoReview.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_eid_info_review

    override val viewModel: EidInfoReviewViewModel
        get() = ViewModelProvider(this).get(EidInfoReviewViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getViewBinding().lifecycleOwner = this
        if (viewModel.parentViewModel?.skipFirstScreen?.value == true) {
            getViewBinding().tbBtnBack.setOnClickListener {
                viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        viewModel.parentViewModel?.uqudoManager?.getPayloadData()?.let { identity ->
            viewModel.populateUqudoState(identity = identity)
        } ?: viewModel.requestAllAPIs(true)
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.ivEditFirstName, R.id.tvFirstName -> {
                    getViewBinding().ivEditFirstName.isEnabled = false
                    getViewBinding().ivEditMiddleName.isEnabled = true
                    getViewBinding().ivEditLastName.isEnabled = true
                    manageFocus(getViewBinding().tvFirstName, getViewBinding().ivEditFirstName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "first_name")
                    )
                }

                R.id.ivEditMiddleName, R.id.tvMiddleName -> {
                    getViewBinding().ivEditMiddleName.isEnabled = false
                    getViewBinding().ivEditFirstName.isEnabled = true
                    getViewBinding().ivEditLastName.isEnabled = true
                    manageFocus(getViewBinding().tvMiddleName, getViewBinding().ivEditMiddleName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "middle_name")
                    )
                }

                R.id.ivEditLastName, R.id.tvLastName -> {
                    getViewBinding().ivEditLastName.isEnabled = false
                    getViewBinding().ivEditMiddleName.isEnabled = true
                    getViewBinding().ivEditFirstName.isEnabled = true
                    manageFocus(getViewBinding().tvLastName, getViewBinding().ivEditLastName)
                    trackEventWithScreenName(
                        FirebaseEvent.EDIT_FIELD,
                        bundleOf("field_name" to "last_name")
                    )
                }

                EidInfoEvents.EVENT_ERROR_INVALID_EID.eventId -> showInvalidEidScreen()
                EidInfoEvents.EVENT_ERROR_EXPIRED_EID.eventId -> showExpiredEidScreen()
                EidInfoEvents.EVENT_ERROE_UNDERAGE.eventId -> showUnderAgeScreen()
                EidInfoEvents.EVENT_ERROR_FROM_USA.eventId -> showUSACitizenScreen()
                EidInfoEvents.EVENT_RESCAN.eventId -> {
                    viewModel.state.BackImage.value = ""
                    viewModel.state.frontImage.value = ""
                    initializeUqudoScanner()
                }
                R.id.tvNoThanks -> {
                    trackEventWithScreenName(FirebaseEvent.RESCAN_ID)
                    hideKeyboard(getViewBinding().tvNoThanks)
                    initializeUqudoScanner()
                }
                EidInfoEvents.EVENT_ALREADY_USED_EID.eventId -> {
                    viewModel.parentViewModel?.finishKyc?.value =
                        DocumentsResponse(false, KYCAction.ACTION_EID_FAILED.name)
                }

                EidInfoEvents.EVENT_NEXT_WITH_ERROR.eventId -> {
                    viewModel.performUqudoUploadDocumentsRequest(true) {
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
                EidInfoEvents.EVENT_FINISH.eventId -> {
                    viewModel.parentViewModel?.finishKyc?.value =
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
                                navigateToConfirmNameFragment()
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
                                viewModel.parentViewModel?.finishKyc?.value =
                                    DocumentsResponse(false, KYCAction.ACTION_EID_UPDATE.name)
                            } else {
                                showToast("Accounts info failed")
                                viewModel.parentViewModel?.finishKyc?.value =
                                    DocumentsResponse(false, KYCAction.ACTION_EID_UPDATE.name)
                            }

                        })
                }
                EidInfoEvents.EVENT_CITIZEN_NUMBER_ISSUE.eventId, EidInfoEvents.EVENT_EID_EXPIRY_DATE_ISSUE.eventId -> invalidCitizenNumber(
                    "Sorry, that didn’t work. Please try again"
                )
            }
        })
        viewModel.eidStateLiveData.observe(viewLifecycleOwner, Observer
        {
            handleState(it)
        })
        viewModel.parentViewModel?.uqudoManager?.getUqudoAccessToken()
            ?.observe(viewLifecycleOwner, Observer { response ->
                if (viewModel.parentViewModel?.uqudoManager?.getPayloadData() == null) {
                    if (response.accessToken.isNullOrEmpty()
                            .not()
                    ) viewModel.eidStateLiveData.postValue(State.empty(""))
                    else viewModel.eidStateLiveData.postValue(
                        State.error("")
                    )
                }
            })

    }

    private fun navigateToConfirmNameFragment() {
        viewModel.parentViewModel?.state?.let { state ->
            state.middleName.set(viewModel.state.middleName)
            state.firstName.set(viewModel.state.firstName)
            state.lastName.set(viewModel.state.lastName)
            state.nationality.set(viewModel.state.nationality)
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

    private fun invalidCitizenNumber(title: String) {
        activity?.let {
            it.showAlertDialogAndExitApp(
                message = title,
                callback = {
                    initializeUqudoScanner()
                },
                closeActivity = false
            )
            viewModel.parentViewModel?.paths?.forEach { filePath ->
                File(filePath).deleteRecursively()
            }
        }
    }

    private fun manageFocus(
        editText: EditText,
        ivEditName: ImageView
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

        editText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                ivEditName.isEnabled = true
                editText.isFocusable = false
                editText.isFocusableInTouchMode = false
            }
        }

        editText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action === KeyEvent.ACTION_DOWN || keyEvent.action === KeyEvent.KEYCODE_ENTER
            ) {
                ivEditName.isEnabled = true
                editText.isFocusable = false
                editText.isFocusableInTouchMode = false
            }
            false
        })


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

    override fun showUSACitizenScreen() {
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.errorTitle, viewModel.errorBody
            )
        navigate(action)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_UQUDO -> {
                val uqudoJWT = data?.getStringExtra("data")
                if (uqudoJWT.isNullOrBlank().not()) {
                    viewModel.state.uqudoToken.value = uqudoJWT
                    UqudoScannerManager.getInstance(requireActivity())
                        .decodeEncodedUqudoToken(uqudoJWT ?: "") {
                            viewModel.eidStateLiveData.postValue(State.success(""))
                        }
                } else {
                    if (viewModel.parentViewModel?.uqudoManager?.getPayloadData() == null && viewModel.parentViewModel?.comingFrom?.value.isNullOrBlank()
                            .not()
                    ) navigateBack() else requireActivity().finish()
                }
            }
            else -> viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)

        }
    }


    override fun onDestroy() {
        viewModel.parentViewModel?.paths?.forEach { filePath ->
            File(filePath).deleteRecursively()
        }
        super.onDestroy()
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.eidStateLiveData.removeObservers(this)
        viewModel.parentViewModel?.uqudoManager?.getUqudoAccessToken()?.removeObservers(this)
        super.onDestroyView()
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
                viewModel.parentViewModel?.uqudoManager?.getPayloadData()?.let { identity ->
                    viewModel.parentViewModel?.payLoadObj?.value = UqudoPayLoad(data = identity)
                    viewModel.populateUqudoState(identity = identity)
                }
            }
            Status.ERROR -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.ERROR
                invalidCitizenNumber(state.message ?: "Sorry, that didn’t work. Please try again")
            }
            Status.IDEAL -> {
                //do nothing
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
                startActivityForResult(this, REQUEST_UQUDO)
            }
        }
    }

    private fun getViewBinding() = getDataBindingView<FragmentEidInfoReviewBinding>()
}