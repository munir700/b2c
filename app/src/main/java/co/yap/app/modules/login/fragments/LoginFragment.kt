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
import co.yap.widgets.guidedtour.TourSetup
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_log_in.*


class LoginFragment : BaseBindingFragment<ILogin.ViewModel>(), ILogin.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_log_in

    override val viewModel: ILogin.ViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isAccountBlocked.observe(this, accountBlockedObserver)
        val sharedPreferenceManager = SharedPreferenceManager.getInstance(requireContext())
        if (sharedPreferenceManager.getValueBoolien(
                KEY_IS_USER_LOGGED_IN,
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
        if (!SharedPreferenceManager.getInstance(requireContext()).getValueBoolien(
                KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            etEmailField.requestKeyboard()
        }

        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.signUpButtonPressEvent.observe(this, signUpButtonObserver)

        viewModel.state.emailError.observe(this, Observer {
            if (!it.isNullOrBlank()) {
                etEmailField.settingUIForError(it)
                etEmailField.settingErrorColor(R.color.error)
            }
        })

        /*
         adding view arrays to start tour on
        */
        activity?.let {
            TourSetup(it, it, setViewsArray())
        }

    }

    fun setViewsArray(): ArrayList<GuidedTourViewDetail> {
        val list = ArrayList<GuidedTourViewDetail>()
        list.add(
            GuidedTourViewDetail(
                ivYap,
                "Your current balance",
                "Here you can see your account’s current balance. It will be updated in-real time after every transaction."
            )
        )
        list.add(
            GuidedTourViewDetail(
                clSignUp,
                "search",
                "Click here to search for specific transaction in your account history"
            )
        )
        list.add(
            GuidedTourViewDetail(
                tvSignIn,
                "yap it",
                "Click here to see more actions like:\n" +
                        "YAP to YAP transactions,  yop up your account, send money and pay your bills"
            )
        )

        list.add(
            GuidedTourViewDetail(
                clSignUp,
                "menu bar",
                "Click here to view the menu bar where you can see your account details and navigate to useful pages"
            )
        )
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isAccountBlocked.removeObservers(this)
    }

    override fun onDestroyView() {
        viewModel.signInButtonPressEvent.removeObservers(this)
        viewModel.signUpButtonPressEvent.removeObservers(this)
        viewModel.state.emailError.removeObservers(this)
        super.onDestroyView()
    }

    private val signInButtonObserver = Observer<Boolean> {
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