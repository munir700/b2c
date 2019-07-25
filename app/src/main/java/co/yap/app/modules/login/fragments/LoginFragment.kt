package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.activities.MainActivity
import co.yap.app.modules.login.activities.VerifyPasscodeActivity
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.viewmodels.LoginViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingFragment

class LoginFragment : BaseBindingFragment<ILogin.ViewModel>(), ILogin.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.screen_log_in

    override val viewModel: ILogin.ViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.signUpButtonPressEvent.observe(this, signUpButtonObserver)
    }

    override fun onDestroyView() {
        viewModel.signInButtonPressEvent.removeObservers(this)
        viewModel.signUpButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val signInButtonObserver = Observer<Boolean> {
        if (it) {
            startActivity(VerifyPasscodeActivity.newIntent(requireContext(), viewModel.state.twoWayTextWatcher))
        } else {
            viewModel.state.emailError =
                Translator.getString(activity as MainActivity, Strings.screen_sign_in_display_text_error_text)
        }
    }

    private val signUpButtonObserver = Observer<Boolean> {
         findNavController().navigate(R.id.action_loginFragment_to_accountSelectionFragment)
    }


}