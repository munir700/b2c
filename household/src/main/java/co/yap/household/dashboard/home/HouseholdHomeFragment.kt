package co.yap.household.dashboard.home

import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.household.databinding.FragmentHouseholdHomeBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.helpers.livedata.SwitchProfileLiveData
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_household_home.*
import kotlinx.android.synthetic.main.layout_drawer_header.*
import kotlinx.android.synthetic.main.layout_drawer_header_expandable.*
import net.cachapa.expandablelayout.ExpandableLayout
import javax.inject.Inject

class HouseholdHomeFragment :
    BaseNavViewModelFragment<FragmentHouseholdHomeBinding, IHouseholdHome.State, HouseHoldHomeVM>() {
    @Inject
    lateinit var mNotificationAdapter: HHNotificationAdapter

    @Inject
    lateinit var mAdapter: HomeTransactionAdapter

    @Inject
    lateinit var mWrappedAdapter: RecyclerView.Adapter<*>

    @Inject
    lateinit var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager

    @Inject
    lateinit var profilePictureAdapter: ProfilePictureAdapter
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_household_home
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setBackButtonDispatcher()
        setupToolbar(mViewDataBinding.toolbar,R.menu.menu_home)
        setHasOptionsMenu(true)
        GetAccountBalanceLiveData.get()
            .observe(this, Observer { state.availableBalance?.value = it?.availableBalance })
        intRecyclersView()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun intRecyclersView() {
        mNotificationAdapter.onItemClickListener = userClickListener
        viewModel.notificationAdapter.set(mNotificationAdapter)
        profilePictureAdapter.onItemClickListener = userClickListener
        viewModel.profilePictureAdapter.set(profilePictureAdapter)
        expandableLayout.setOnExpansionUpdateListener { _, state ->
            when (state) {
                ExpandableLayout.State.EXPANDED -> ivChevron.rotation = 180F
                ExpandableLayout.State.COLLAPSED -> ivChevron.rotation = 0F
            }
        }
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        mViewDataBinding.lyInclude.recyclerView.apply {
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.transactionAdapter?.set(mAdapter)
            setHasFixedSize(false)
        }
    }

    private val userClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AccountInfo) {
                if (data.accountType == AccountType.B2C_ACCOUNT.name) {
                    data.uuid?.let {
                        SwitchProfileLiveData.get(it, this@HouseholdHomeFragment)
                            .observe(this@HouseholdHomeFragment, Observer<AccountInfo?> {
                                launchActivity<YapDashboardActivity>(clearPrevious = true)
                            })
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        drawerLayout.openDrawer(GravityCompat.END)
        return super.onOptionsItemSelected(item)
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnCopyHH -> {
            }
            R.id.lyHeader_section -> expandableLayout.toggle(true)
        }
    }

    override fun setHomeAsUpIndicator() = R.drawable.ic_search_white
    override fun toolBarVisibility() = false

    override fun onBackPressed(): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) drawerLayout.closeDrawer(GravityCompat.END) else finishActivityAffinity()
        return super.onBackPressed()
    }
}
