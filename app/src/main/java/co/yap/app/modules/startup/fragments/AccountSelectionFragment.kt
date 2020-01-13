package co.yap.app.modules.startup.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.startup.interfaces.IAccountSelection
import co.yap.app.modules.startup.viewmodels.AccountSelectionViewModel
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.BaseBindingFragment

class AccountSelectionFragment : BaseBindingFragment<IAccountSelection.ViewModel>(),
    IAccountSelection.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_account_selection

    override val viewModel: IAccountSelection.ViewModel
        get() = ViewModelProviders.of(this).get(AccountSelectionViewModel::class.java)

    override fun onResume() {
        super.onResume()
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tvSignIn -> {
                    findNavController().navigate(R.id.action_accountSelectionFragment_to_loginFragment)
                }
                R.id.btnBusiness -> {
                    findNavController().navigate(R.id.householdOnboard)
                }
                R.id.btnPersonal -> {

                    findNavController().navigate(
                        R.id.action_accountSelectionFragment_to_welcomeFragment,
                        Bundle().apply {
                            putSerializable(
                                getString(R.string.arg_account_type),
                                AccountType.B2C_ACCOUNT
                            )
                        })
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
    }
}