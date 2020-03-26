package co.yap.household.dashboard.subaccounts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseHoldAccountsParentBinding
import co.yap.yapcore.BaseBindingFragment
import com.google.android.material.tabs.TabLayoutMediator

class HouseHoldAccountsParentFragment : BaseBindingFragment<IHouseHoldAccountsParent.ViewModel>(),
    IHouseHoldAccountsParent.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_accounts_parent

    override val viewModel: IHouseHoldAccountsParent.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldAccountsParentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setupTabs()
    }


    override fun setObservers() {
       viewModel.clickEvent.observe(this,clickEvent)
    }
    var clickEvent=Observer<Int>{}
    override fun removeObservers() {
    }
    private fun setUpAdapter() {
        val adaptor = HouseHoldSubAccountsParentAdapter(this)
        getBindings().viewPager.adapter = adaptor
    }

    private fun setupTabs() {
        TabLayoutMediator(getBindings().tabLayout, getBindings().viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = getTabTitle(position)
            }).attach()

        getBindings().viewPager.isUserInputEnabled = false
        getBindings().viewPager.offscreenPageLimit = 1
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            SUB_ACCOUNTS -> "cards"

            ANALYTICS -> "Analytics"
            else -> null
        }
    }


    private fun getBindings(): FragmentHouseHoldAccountsParentBinding {
        return viewDataBinding as FragmentHouseHoldAccountsParentBinding
    }
}