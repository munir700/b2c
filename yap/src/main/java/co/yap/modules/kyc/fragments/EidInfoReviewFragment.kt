package co.yap.modules.kyc.fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEidInfoReviewBinding
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.enums.KYCAction
import co.yap.modules.kyc.viewmodels.EidInfoReviewViewModel
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils.hideKeyboard
import co.yap.yapcore.helpers.showAlertDialogAndExitApp
import co.yap.yapcore.managers.SessionManager
import io.uqudo.sdk.core.DocumentBuilder
import io.uqudo.sdk.core.UqudoBuilder
import io.uqudo.sdk.core.domain.model.DocumentType
import java.io.File

class EidInfoReviewFragment : KYCChildFragment<IEidInfoReview.ViewModel>(), IEidInfoReview.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_eid_info_review

    override val viewModel: EidInfoReviewViewModel
        get() = ViewModelProvider(this).get(EidInfoReviewViewModel::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getViewBinding().lifecycleOwner = this
        if (viewModel.parentViewModel?.skipFirstScreen?.value == true) {
            getViewBinding().tbBtnBack.setOnClickListener {
                viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
            }
        }
        addObservers()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestAllAPIs(true)
        viewModel.state.payLoadObj.value?.let { identity ->
            viewModel.populateUqudoState(identity = identity)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
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

                viewModel.eventErrorInvalidEid -> showInvalidEidScreen()
                viewModel.eventErrorExpiredEid -> showExpiredEidScreen()
                viewModel.eventErrorUnderAge -> showUnderAgeScreen()
                viewModel.eventErrorFromUsa -> showUSACitizenScreen()
                viewModel.eventRescan -> {
                    if (viewModel.state.isExpired.value == true) viewModel.requestAllAPIs(false) else initializeUqudoScanner()
                }
                R.id.tvNoThanks -> {
                    trackEventWithScreenName(FirebaseEvent.RESCAN_ID)
                    hideKeyboard(getViewBinding().tvNoThanks)
                    if (viewModel.state.isExpired.value == true) viewModel.requestAllAPIs(false) else initializeUqudoScanner()
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
                                navigateToConfirmNameFragment()
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
        viewModel.eidStateLiveData.observe(viewLifecycleOwner, Observer
        {
            handleState(it)
        })
        viewModel.uqudoResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.accessToken.isNullOrEmpty()
                    .not() && viewModel.state.isTokenValid.get()
            ) viewModel.eidStateLiveData.postValue(State.empty("")) else viewModel.eidStateLiveData.postValue(
                State.error("")
            )
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun invalidCitizenNumber(title: String) {
        activity?.let {
            it.showAlertDialogAndExitApp(
                message = title,
                callback = {
                    if (viewModel.state.isExpired.value == true) viewModel.requestAllAPIs(false) else initializeUqudoScanner()
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
            REQUEST_CODE -> {
                viewModel.state.uqudoToken.value = data?.getStringExtra("data")
                if (viewModel.state.uqudoToken.value.isNullOrBlank().not()) {
                    viewModel.extractJwt(viewModel.state.uqudoToken.value)
                } else {
                    navigateBack()
                }
            }
            else -> viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)

        }
        /*if (data == null && viewModel.parentViewModel?.skipFirstScreen?.value == true) {
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


    override fun onDestroy() {
        viewModel.parentViewModel?.paths?.forEach { filePath ->
            File(filePath).deleteRecursively()
        }
        super.onDestroy()
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
                viewModel.state.payLoadObj.value?.let { identity ->
                    viewModel.populateUqudoState(identity = identity)
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

    private val REQUEST_CODE get() = 1011

    private fun initializeUqudoScanner() {
        try {
            val doc = DocumentBuilder(requireActivity().applicationContext)
                .setDocumentType(DocumentType.UAE_ID).disableHelpPage().enableReading()
                .enableScanReview(true, true)
                .build()
            val uqudoIntent = UqudoBuilder.Enrollment()
                .setToken(viewModel.uqudoResponse.value?.accessToken ?: "")
                .disableSecureWindow()
                .add(doc)
                .build(requireActivity().applicationContext)
            startActivityForResult(uqudoIntent, REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun getViewBinding() = getDataBindingView<FragmentEidInfoReviewBinding>()
}