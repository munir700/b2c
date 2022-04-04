package co.yap.app.modules.login.fragments

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
import co.yap.databinding.FragmentNotificationsHomeV2Binding
import co.yap.widgets.keyboardvisibilityevent.KeyboardVisibilityEvent
import co.yap.widgets.keyboardvisibilityevent.KeyboardVisibilityEventListener
import co.yap.yapcore.constants.Constants.KEY_IS_REMEMBER
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.scrollToBottomWithoutFocusChange
import co.yap.yapcore.managers.SessionManager


class LoginFragment : MainChildFragment<ILogin.ViewModel>(), ILogin.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_log_in

    override val viewModel: LoginViewModel
        get() = ViewModelProvider(this).get(LoginViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isAccountBlocked.observe(viewLifecycleOwner, accountBlockedObserver)
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
            getDataBindingView<FragmentLogInBinding>().etEmailField.requestKeyboard()
        }

        SessionManager.isRemembered.value =
            sharedPreferenceManager.getValueBoolien(KEY_IS_REMEMBER, true)
        SessionManager.isRemembered.value?.let {
            getDataBindingView<FragmentLogInBinding>().etEmailField.editText.setText(if (it) sharedPreferenceManager.getDecryptedUserName() else "")
            if (getDataBindingView<FragmentLogInBinding>().etEmailField.editText.length() > 1) getDataBindingView<FragmentLogInBinding>().etEmailField.editText.setSelection(getDataBindingView<FragmentLogInBinding>().etEmailField.editText.length())
        }
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.signUpButtonPressEvent.observe(this, signUpButtonObserver)
        viewModel.state.emailError.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()) {
                getDataBindingView<FragmentLogInBinding>().etEmailField.settingUIForError(it)
                getDataBindingView<FragmentLogInBinding>().etEmailField.settingErrorColor(R.color.error)
            }
        })
        KeyboardVisibilityEvent.setEventListener(requireActivity(), viewLifecycleOwner, object :
            KeyboardVisibilityEventListener {
            override fun onVisibilityChanged(isOpen: Boolean) {
                getDataBindingView<FragmentLogInBinding>().clSignUp.post {
                    if (isOpen)
                        getDataBindingView<FragmentLogInBinding>().scrollView.scrollToBottomWithoutFocusChange()
                    getDataBindingView<FragmentLogInBinding>().clSignUp.visibility = if (isOpen) GONE else VISIBLE
                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.isAccountBlocked.removeObservers(this)
        viewModel.signInButtonPressEvent.removeObservers(this)
        viewModel.signUpButtonPressEvent.removeObservers(this)
        viewModel.state.emailError.removeObservers(this)
        super.onDestroyView()
    }

    private val signInButtonObserver = Observer<Boolean> {
        navigateToPassCode()
    }

    private fun navigateToPassCode() {
        val action =
            LoginFragmentDirections.actionLoginFragmentToVerifyPasscodeFragment(
                viewModel.state.twoWayTextWatcher,
                isAccountBlocked = false
            )
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.state.twoWayTextWatcher = ""
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

    private val signUpButtonObserver = Observer<Boolean> {
        findNavController().navigate(R.id.action_loginFragment_to_accountSelectionFragment)
    }
}