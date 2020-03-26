package co.yap.household.dashboard.subaccounts.householdsubaccounts

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.viewModel

class HouseHoldSubAccountsFragment : BaseBindingFragment<IHouseHoldSubAccounts.ViewModel>(),
    IHouseHoldSubAccounts.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_sub_accounts

    override val viewModel: IHouseHoldSubAccounts.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldSubAccountsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}