package co.yap.app.modules.login.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.databinding.FragmentLogInBinding
import co.yap.app.main.MainChildFragment
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.viewmodels.LoginViewModel
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.widgets.keyboardvisibilityevent.KeyboardVisibilityEvent
import co.yap.widgets.keyboardvisibilityevent.KeyboardVisibilityEventListener
import co.yap.yapcore.constants.Constants.KEY_IS_REMEMBER
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import kotlinx.android.synthetic.main.fragment_log_in.*


class LoginFragment : MainChildFragment<ILogin.ViewModel>(), ILogin.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_log_in

    override val viewModel: LoginViewModel
        get() = ViewModelProvider(this).get(LoginViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiatePreference()
        configureWindow()
        setTouchListener()
        setObservers()
    }

    private fun configureWindow() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.requestDefaultFocus()
    }

    private fun initiatePreference() {
        val sharedPreferenceManager = SharedPreferenceManager.getInstance(requireContext())
        if (sharedPreferenceManager.getValueBoolien(
                KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            val action =
                LoginFragmentDirections.actionLoginFragmentToVerifyPasscodeFragment("")
            NavHostFragment.findNavController(this).navigate(action)
        } else {
            getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.requestDefaultFocus()
            // etEmailField.requestKeyboard()
        }

        SessionManager.isRemembered.value =
            sharedPreferenceManager.getValueBoolien(KEY_IS_REMEMBER, true)
        SessionManager.isRemembered.value?.let {
            etEmailField.editText.setText(if (it) sharedPreferenceManager.getDecryptedUserName() else "")
            if (etEmailField.editText.length() > 1) etEmailField.editText.setSelection(etEmailField.editText.length())
        }
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(viewLifecycleOwner, clickListenerHandler)
        viewModel.isAccountBlocked.observe(viewLifecycleOwner, accountBlockedObserver)
        viewModel.state.emailError.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()) {
                etEmailField.settingUIForError(it)
                etEmailField.settingErrorColor(R.color.error)
            }
        })
        KeyboardVisibilityEvent.setEventListener(requireActivity(), viewLifecycleOwner, object :
            KeyboardVisibilityEventListener {
            override fun onVisibilityChanged(isOpen: Boolean) {
                clSignUp?.post {
                    if (isOpen)
                        scrollView?.scrollToBottomWithoutFocusChange()
                    clSignUp?.visibility = if (isOpen) GONE else VISIBLE
                }
            }
        })
    }

    private fun navigateToPassCode() {
        val action =
            LoginFragmentDirections.actionLoginFragmentToVerifyPasscodeFragment(
                viewModel.state.mobileNumber.value ?: "",
                isAccountBlocked = false
            )
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.state.mobileNumber.value = ""
    }


    private val clickListenerHandler = Observer<Int> { id ->
        when (id) {
            R.id.btnLogIn -> {
                viewModel.state.mobileNumber.value = Utils.verifyUsername(
                    viewModel.state.mobile.get()?.filter { it.isWhitespace().not() }?.trim() ?: ""
                )
                viewModel.validateUsername { error ->
                    if (error.isNullOrEmpty()
                            .not()
                    ) getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.error =
                        error else navigateToPassCode()
                }
            }
            R.id.tvSignUp -> findNavController().navigate(R.id.action_loginFragment_to_accountSelectionFragment)

        }
    }

    private val accountBlockedObserver = Observer<Boolean> { isAccountBlocked ->
        if (isAccountBlocked) {
            val action =
                LoginFragmentDirections.actionLoginFragmentToVerifyPasscodeFragment(
                    viewModel.state.twoWayTextWatcher,
                    isAccountBlocked
                )
            NavHostFragment.findNavController(this).navigate(action)
            viewModel.state.twoWayTextWatcher = ""
        }
    }

    private val selectCountryItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is CoreBottomSheetData) {
                getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.setStartIconDrawable(
                    requireContext().getDropDownIconByName(
                        data.key ?: "AE"
                    )
                )
                viewModel.state.mobile.set("")
                viewModel.state.countryCode.set(data.content.toString())
                getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.requestFocusForField()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener() {
        getDataBindingView<FragmentLogInBinding>().etMobileNumber.setTouchListener {
            getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.hideKeyboard()
            activity?.let { context ->
                context.launchBottomSheetForMutlipleCountries(
                    selectCountryItemClickListener
                )
            }
        }
    }

    private fun removeObserver() {
        viewModel.isAccountBlocked.removeObservers(this)
        viewModel.state.emailError.removeObservers(this)
        viewModel.clickEvent.removeObserver(clickListenerHandler)
    }

    override fun onDestroyView() {
        removeObserver()
        super.onDestroyView()
    }
}
