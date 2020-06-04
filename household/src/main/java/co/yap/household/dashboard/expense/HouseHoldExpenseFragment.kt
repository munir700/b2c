package co.yap.household.dashboard.expense

import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseHoldExpenseBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HouseHoldExpenseFragment :
    BaseNavViewModelFragment<FragmentHouseHoldExpenseBinding, IHouseHoldExpense.State, HouseHoldExpenseVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_house_hold_expense
    override fun toolBarVisibility() = false
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setHasOptionsMenu(false)
    }
}