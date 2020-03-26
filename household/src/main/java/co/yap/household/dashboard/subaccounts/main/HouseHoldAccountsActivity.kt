package co.yap.household.dashboard.subaccounts.main

import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class HouseHoldAccountsActivity : BaseBindingActivity<IHouseHoldAccounts.ViewModel>(),
    IHouseHoldAccounts.View, INavigator,
    IFragmentHolder {
    override val viewModel: IHouseHoldAccounts.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldAccountsViewModel::class.java)


    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.houseHoldAccountsNavigation)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_house_hold_accounts
}