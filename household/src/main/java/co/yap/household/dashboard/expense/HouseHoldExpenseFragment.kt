package co.yap.household.dashboard.expense

import android.os.Bundle
import androidx.fragment.app.viewModels
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseHoldExpenseBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HouseHoldExpenseFragment :
    BaseNavViewModelFragmentV2<FragmentHouseHoldExpenseBinding, IHouseHoldExpense.State, HouseHoldExpenseVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override val viewModel: HouseHoldExpenseVM by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_expense
    override fun toolBarVisibility() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        //setupToolbar(mViewDataBinding.toolbar)
    }
    override fun setHomeAsUpIndicator() = R.drawable.ic_search_white
    override fun onClick(id: Int) {
    }
}