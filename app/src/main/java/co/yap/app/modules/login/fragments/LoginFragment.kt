package co.yap.app.modules.login.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
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
import co.yap.yapcore.helpers.extentions.getDropDownIconByName
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.helpers.extentions.launchBottomSheetForMutlipleCountries
import co.yap.yapcore.helpers.extentions.scrollToBottomWithoutFocusChange
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
        viewModel.clickEvent.observe(viewLifecycleOwner, clickListenerHandler)
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
            etEmailField.requestKeyboard()
        }

        SessionManager.isRemembered.value =
            sharedPreferenceManager.getValueBoolien(KEY_IS_REMEMBER, true)
        SessionManager.isRemembered.value?.let {
            etEmailField.editText.setText(if (it) sharedPreferenceManager.getDecryptedUserName() else "")
            if (etEmailField.editText.length() > 1) etEmailField.editText.setSelection(etEmailField.editText.length())
        }
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.signUpButtonPressEvent.observe(this, signUpButtonObserver)
        setTouchListener()
        getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.requestFocus()
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

    private val clickListenerHandler = Observer<Int> { id ->
        when (id) {
            R.id.btnLogIn -> {
                viewModel.state.mobileNumber.value = Utils.verifyUsername(
                    viewModel.state.mobile.get()?.filter { it.isWhitespace().not() }?.trim() ?: ""
                )
                viewModel.validateUsername {error ->
                 if (error.isNullOrEmpty().not())   getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.error = error else navigateToPassCode()
                }
            }
        }
    }

    private fun navigateToPassCode() {
        val action =
            LoginFragmentDirections.actionLoginFragmentToVerifyPasscodeFragment(
                viewModel.state.mobileNumber.value?:"",
                isAccountBlocked = false
            )
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.state.mobileNumber.value = ""
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

    private val selectCountryItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is CoreBottomSheetData) {
                getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.setStartIconDrawable(
                    requireContext().getDropDownIconByName(
                        data.key ?: "PK"
                    )
                )
                viewModel.state.mobile.set("")
                viewModel.state.countryCode.set(data.content.toString())
                getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.requestFocus()
                //  getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.showKeyboard()
                getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.boxStrokeColor =
                    resources.getColor(R.color.colorPrimary)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener() {
        getDataBindingView<FragmentLogInBinding>().etMobileNumber.setOnTouchListener(View.OnTouchListener { v, event ->
            val drawableLeft = 0
            if (event.action == MotionEvent.ACTION_UP && event.rawX <=
                etMobileNumber.compoundDrawables[drawableLeft].bounds.width()
            ) {

                getDataBindingView<FragmentLogInBinding>().tlPhoneNumber.hideKeyboard()
                activity?.let { context ->
                    context.launchBottomSheetForMutlipleCountries(
                        selectCountryItemClickListener
                    )
                }
                return@OnTouchListener true
            }
            false
        })
    }
}