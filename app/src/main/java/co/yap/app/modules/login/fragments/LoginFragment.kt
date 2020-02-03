package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.viewmodels.LoginViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_log_in.*

class LoginFragment : BaseBindingFragment<ILogin.ViewModel>(), ILogin.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_log_in

    override val viewModel: ILogin.ViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferenceManager = SharedPreferenceManager(requireContext())
        if (sharedPreferenceManager.getValueBoolien(
                SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            val action =
                LoginFragmentDirections.actionLoginFragmentToVerifyPasscodeFragment("")
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.signUpButtonPressEvent.observe(this, signUpButtonObserver)
        viewModel.state.emailError.observe(this, Observer {
            if (!it.isNullOrBlank()) {
                etEmailField.settingUIForError(it)
                etEmailField.settingErrorColor(R.color.error)
            }
        })

    }

    override fun onDestroyView() {
        viewModel.signInButtonPressEvent.removeObservers(this)
        viewModel.signUpButtonPressEvent.removeObservers(this)
        viewModel.state.emailError.removeObservers(this)
        super.onDestroyView()
    }

    private val signInButtonObserver = Observer<Boolean> {
        val action =
            LoginFragmentDirections.actionLoginFragmentToVerifyPasscodeFragment(viewModel.state.twoWayTextWatcher)
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.state.twoWayTextWatcher = ""
    }

    private val signUpButtonObserver = Observer<Boolean> {
        findNavController().navigate(R.id.action_loginFragment_to_accountSelectionFragment)
    }

}