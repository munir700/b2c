package co.yap.household.dashboard.subaccounts

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.yap.household.dashboard.subaccounts.householdsubaccounts.HouseHoldSubAccountsFragment

const val SUB_ACCOUNTS = 0
const val ANALYTICS = 1

class HouseHoldSubAccountsParentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        SUB_ACCOUNTS to { HouseHoldSubAccountsFragment() },
        ANALYTICS to { HouseHoldSubAccountsFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}