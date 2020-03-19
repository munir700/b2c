package co.yap.household.dashboard2.main

import androidx.navigation.ui.setupWithNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.dashboard2.home.HouseholdHomeFragment
import co.yap.household.databinding.ActivityHouseholdDashboardBinding
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.BaseViewModelActivity
import kotlinx.android.synthetic.main.activity_household_dashboard.*
import javax.inject.Inject

class HouseholdDashboardActivity :
    BaseViewModelActivity<ActivityHouseholdDashboardBinding, IHouseholdDashboard.State, HouseHoldDashBoardVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.activity_household_dashboard

    override fun postInit() {
        super.postInit()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        viewModel.adapter?.apply {
            set(adapter)
        }
        bottomNav.setUpWithViewPager(viewPager)
    }
}