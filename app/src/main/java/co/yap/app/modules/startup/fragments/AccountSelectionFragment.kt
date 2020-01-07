package co.yap.app.modules.startup.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.startup.interfaces.IAccountSelection
import co.yap.app.modules.startup.viewmodels.AccountSelectionViewModel
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_account_selection.*

class AccountSelectionFragment : BaseBindingFragment<IAccountSelection.ViewModel>(),
    IAccountSelection.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_account_selection

    override val viewModel: IAccountSelection.ViewModel
        get() = ViewModelProviders.of(this).get(AccountSelectionViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tvSignIn -> {
                    tvSignIn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_accountSelectionFragment_to_loginFragment))
                    tvSignIn.performClick()
                }
                R.id.btnBusiness -> {

                }
                R.id.btnPersonal -> {
                    btnPersonal.setOnClickListener(
                        Navigation.createNavigateOnClickListener(
                            R.id.action_accountSelectionFragment_to_welcomeFragment,
                            Bundle().apply {
                                putSerializable(
                                    getString(R.string.arg_account_type),
                                    AccountType.B2C_ACCOUNT
                                )
                            })
                    )
                    btnPersonal.performClick()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
    }
}