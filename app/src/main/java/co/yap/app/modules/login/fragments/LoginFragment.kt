package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.main.MainChildFragment
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.viewmodels.LoginViewModel
import co.yap.modules.dashboard.store.young.card.YoungCardEditDetailsFragment
import co.yap.modules.dashboard.store.young.confirmrelationship.YoungConfirmRelationshipFragment
import co.yap.modules.dashboard.store.young.contact.fragments.YoungContactDetailsFragment
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.startFragment
import kotlinx.android.synthetic.main.fragment_log_in.*

class LoginFragment : MainChildFragment<ILogin.ViewModel>(), ILogin.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_log_in
    override val viewModel: LoginViewModel
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
        if (viewModel.parentViewModel?.shardPrefs?.getValueBoolien(
                KEY_IS_USER_LOGGED_IN,
                false
            ) == false
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
        tvSignUpPrefix.setOnClickListener {
//            requireContext().getJsonDataFromAsset("hh_user_existing.json")?.let {
//                val user = GsonProvider.fromJson(
//                    it, AccountInfoResponse::class.java
//                )
//                MyUserManager.usersList?.value = ArrayList(user.data)
//                MyUserManager.user = MyUserManager.getCurrentUser()
//                MyUserManager.user?.notificationStatuses = AccountStatus.INVITE_ACCEPTED.name
//                launchActivity<OnBoardingHouseHoldActivity>() {
//                    putExtra(NAVIGATION_Graph_ID, R.navigation.hh_new_user_onboarding_navigation)
//                    putExtra(
//                        NAVIGATION_Graph_START_DESTINATION_ID,
//                        R.id.HHOnBoardingWelcomeFragment
//                    )
//                }
//            }
            startFragment(YoungCardEditDetailsFragment::class.java.name)
        }
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