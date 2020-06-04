package co.yap.household.dashboard.cards

import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentMyCardBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.ChangeCardPinActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.modules.others.helper.Constants
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.DividerItemDecoration
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
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
    BaseRecyclerViewFragment<FragmentMyCardBinding, IMyCard.State, MyCardVM, MyCardFragment.Adapter, Transaction>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_my_card

    @Inject
    lateinit var optionList: ArrayList<Option>

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setupToolbar(mViewDataBinding.toolbar, R.menu.menu_options)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                R.drawable.item_divider,
                showFirstDivider = false,
                showLastDivider = false,
                marginStart = dimen(co.yap.R.dimen._70sdp)
            )
        )
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.getPrimaryCard() {
            setHasOptionsMenu(true)
        }
        viewModel.checkFreezeUnfreezeStatus()
    }

    override fun toolBarVisibility() = false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showBottomSheet()
        return super.onOptionsItemSelected(item)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            viewModel.EVENT_FREEZE_UNFREEZE_CARD -> {
                viewModel.checkFreezeUnfreezeStatus()
            }
            R.id.btnCardDetails -> {
                viewModel.getCardDetails()
            }
            viewModel.EVENT_CARD_DETAILS -> {
                requireContext().showCardDetailsPopup(
                    viewModel.state.cardDetail?.value,
                    viewModel.state.card?.value
                )
            }
        }
    }

    private fun showBottomSheet() {
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
                    R.id.freeze_card.toLong() -> viewModel.freezeUnfreezeCard()
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

    override fun setHomeAsUpIndicator() = R.drawable.ic_search_white

    class Adapter(mValue: MutableList<Transaction>, navigation: NavController?) :
        BaseRVAdapter<Transaction, MyCardRecentTransactionsItemVM, BaseViewHolder<Transaction, MyCardRecentTransactionsItemVM>>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: MyCardRecentTransactionsItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ) = BaseViewHolder(view, viewModel, mDataBinding)

        override fun getViewModel(viewType: Int) = MyCardRecentTransactionsItemVM()
        override fun getVariableId() = BR.viewModel
    }
}
