package co.yap.household.dashboard.cards

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.dashboard.home.HomeTransactionAdapter
import co.yap.household.databinding.FragmentMyCardBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.ChangeCardPinActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.modules.others.helper.Constants
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.showCardDetailsPopup
import com.arthurivanets.bottomsheets.ktx.actionPickerConfig
import com.arthurivanets.bottomsheets.ktx.showActionPickerBottomSheet
import com.arthurivanets.bottomsheets.sheets.listeners.OnItemSelectedListener
import com.arthurivanets.bottomsheets.sheets.model.Option
import javax.inject.Inject

class MyCardFragment :
    BaseNavViewModelFragment<FragmentMyCardBinding, IMyCard.State, MyCardVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_my_card

    @Inject
    lateinit var optionList: ArrayList<Option>

    @Inject
    lateinit var mAdapter: HomeTransactionAdapter

    @Inject
    lateinit var mWrappedAdapter: RecyclerView.Adapter<*>

    @Inject
    lateinit var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setupToolbar(mViewDataBinding.toolbar, R.menu.menu_hh_options, setActionBar = false)
        intRecyclersView()
//        setHasOptionsMenu(true)
        viewModel.getPrimaryCard() {
            setHasOptionsMenu(it)
        }
        viewModel.checkFreezeUnfreezeStatus()
    }

    override fun toolBarVisibility() = false
    override fun setHomeAsUpIndicator() = R.drawable.ic_search_white
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_hh_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showBottomSheet()
        return super.onOptionsItemSelected(item)
    }

    private fun intRecyclersView() {
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        mViewDataBinding.recyclerView.apply {
            addItemDecoration(StickyHeaderItemDecoration())
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.transactionAdapter?.set(mAdapter)
            setHasFixedSize(false)
        }
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnCardDetails -> {
                viewModel.getCardDetails() {
                    requireContext().showCardDetailsPopup(
                        viewModel.state.cardDetail?.value,
                        viewModel.state.card?.value
                    )
                }
            }
        }
    }

    private fun showBottomSheet() {
        state.cardStatus.value?.let { cardStatus ->
            optionList[1] = Option().setTitle(
                cardStatus
            )
        }

        showActionPickerBottomSheet(
            options = optionList,
            config = actionPickerConfig() {
                sheetAnimationDuration(300L)
                    .topGapSize(dimen(co.yap.R.dimen.margin_extra_small).toFloat())
            },
            onItemSelectedListener = OnItemSelectedListener {
                when (it.id) {
                    R.id.change_pin.toLong() -> {
                        launchActivity<ChangeCardPinActivity>() {
                            putExtra(
                                "cardSerialNumber",
                                viewModel.state.card?.value?.cardSerialNumber
                            )
                        }
                    }
                    R.id.freeze_card.toLong() -> {
                        viewModel.freezeUnfreezeCard() {
                            viewModel.checkFreezeUnfreezeStatus()
                        }
                    }
                    R.id.view_statement.toLong() -> {
                        launchActivity<CardStatementsActivity> {
                            putExtra("card", viewModel.state.card?.value)
                            putExtra("isFromDrawer", false)
                        }
                    }
                    R.id.report_lost_card.toLong() -> {
                        viewModel.state.card?.value?.let { card ->
                            launchActivity<ReportLostOrStolenCardActivity>(requestCode = Constants.REQUEST_REPORT_LOST_OR_STOLEN) {
                                putExtra(
                                    "card",
                                    card
                                )
                            }
                        }
                    }
                    R.id.cancel.toLong() -> toast("Cancel")
                }
            }
        )
    }

}
