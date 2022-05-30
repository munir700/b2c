package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhsalaryProfileBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.subaccounts.paysalary.profile.adapter.HHSalaryProfileTransfersAdapter
import co.yap.modules.subaccounts.paysalary.profile.adapter.SalarySetupAdapter
import co.yap.translation.Strings
import co.yap.widgets.SpacesItemDecoration
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.constants.Constants

import co.yap.yapcore.helpers.alert
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.interfaces.OnItemClickListener
import com.arthurivanets.bottomsheets.ktx.actionPickerConfig
import com.arthurivanets.bottomsheets.ktx.showActionPickerBottomSheet
import com.arthurivanets.bottomsheets.sheets.listeners.OnItemSelectedListener
import com.arthurivanets.bottomsheets.sheets.model.Option
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hhsalary_profile.*
import javax.inject.Inject

@AndroidEntryPoint
class HHSalaryProfileFragment :
    BaseNavViewModelFragmentV2<FragmentHhsalaryProfileBinding, IHHSalaryProfile.State, HHSalaryProfileVM>(),
    OnItemClickListener {
    /*@Inject TODO revert it when migration done
    lateinit var mSalarySetupAdapter: SalarySetupAdapter*/

    // TODO Remove it when migration done
    val mSalarySetupAdapter: SalarySetupAdapter by lazy {
        SalarySetupAdapter(
            getNoTransactionsData(),
            null
        )
    }

    // TODO need to be injected by Hilt after migration
    fun getNoTransactionsData(): ArrayList<PaySalaryModel> {
        return ArrayList<PaySalaryModel>().apply {
            add(
                PaySalaryModel(
                    Strings.screen_house_hold_salary_profile_set_up_salary_text,
                    R.drawable.ic_transaction_rate_arrow
                )
            )
            add(
                PaySalaryModel(
                    Strings.screen_house_hold_salary_profile_set_up_expense_text,
                    R.drawable.ic_expense
                )
            )
            add(
                PaySalaryModel(
                    Strings.screen_house_hold_salary_profile_transfer_bonus_text,
                    R.drawable.ic_yap_to_yap
                )
            )

        }
    }

    val salaryTransferAdapter: HHSalaryProfileTransfersAdapter by lazy {
        HHSalaryProfileTransfersAdapter(
            emptyMap()
        )
    }

    val mWrappedAdapter: RecyclerView.Adapter<*>  by lazy {
        mRecyclerViewExpandableItemManager.createWrappedAdapter(salaryTransferAdapter)
    }

    val mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager by lazy {
        RecyclerViewExpandableItemManager(null)
    }

    /*@Inject TODO revert it when migration done
    lateinit var salaryTransferAdapter: HHSalaryProfileTransfersAdapter

    @Inject
    lateinit var mWrappedAdapter: RecyclerView.Adapter<*>

    @Inject
    lateinit var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager */

    override val viewModel: HHSalaryProfileVM by viewModels()

    override fun getBindingVariable() = BR.hhSalaryProfileVM
    override fun getLayoutId() = R.layout.fragment_hhsalary_profile

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setHasOptionsMenu(true)
        recyclerView.adapter = mSalarySetupAdapter
        mSalarySetupAdapter.onItemClickListener = this
        recyclerView.addItemDecoration(SpacesItemDecoration(dimen(co.yap.yapcore.R.dimen.margin_normal), true))
    }

    private fun intRecyclersView() {
        mSalarySetupAdapter.onItemClickListener = this
        viewModel.salarySetupAdapter?.set(mSalarySetupAdapter)
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        mViewDataBinding.recyclerView.apply {
            addItemDecoration(StickyHeaderItemDecoration())
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.transactionAdapter?.set(salaryTransferAdapter)
            // pagination = viewModel.getPaginationListener()
            setHasFixedSize(false)
        }
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.ivSalary -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToPayHHEmployeeSalaryFragment(),
                arguments
            )
            R.id.ivExpenses -> alert("Coming Soon")
            R.id.ivUserImage -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToHHProfileFragment(),
                arguments
            )
            R.id.ivTransfer -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToHHIbanSendMoneyFragment(),
                arguments
            )
        }
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
        when (pos) {
            0 -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToPayHHEmployeeSalaryFragment(),
                arguments
            )
            1 -> alert("Coming Soon")
            2 -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToHHIbanSendMoneyFragment(),
                arguments
            )
        }
    }

    override fun getToolBarTitle() = viewModel.state.subAccount.value?.getFullName()
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showActionPickerBottomSheet(
            options = ArrayList<Option>().apply {
                add(Option().setId(1L).setTitle("Subscription"))
                add(Option().setId(2L).setTitle("Salary statements"))
            },
            config = actionPickerConfig {
                sheetAnimationDuration(300L)
                    .topGapSize(dimen(R.dimen.margin_extra_small).toFloat())
            },
            onItemSelectedListener = OnItemSelectedListener {
                when (it.title) {
                    "Subscription" -> navigateForwardWithAnimation(
                        HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToSubscriptionFragment(),
                        arguments
                    )
                    "Salary statements" -> {
                        launchActivity<CardStatementsActivity> {
                            putExtra("isFromDrawer", false)
                            putExtra(Constants.ACCOUNT_UUID, viewModel.state.subAccount.value?.accountUuid)
                        }
                    }
                }
            }
        )
        return super.onOptionsItemSelected(item)
    }
}