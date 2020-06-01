package co.yap.household.dashboard.cards

import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentMyCardBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.widgets.DividerItemDecoration
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.toast
import com.arthurivanets.bottomsheets.ktx.actionPickerConfig
import com.arthurivanets.bottomsheets.ktx.showActionPickerBottomSheet
import com.arthurivanets.bottomsheets.sheets.listeners.OnItemSelectedListener
import com.arthurivanets.bottomsheets.sheets.model.Option

class MyCardFragment :
    BaseRecyclerViewFragment<FragmentMyCardBinding, IMyCard.State, MyCardVM, MyCardFragment.Adapter, Transaction>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_my_card

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setupToolbar(mViewDataBinding.toolbar,R.menu.menu_options)
        setHasOptionsMenu(true)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                R.drawable.item_divider,
                showFirstDivider = false,
                showLastDivider = false,
                marginStart = dimen(co.yap.R.dimen._70sdp)
            )
        )
    }
    override fun toolBarVisibility() = false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showBottomSheet()
        return super.onOptionsItemSelected(item)
    }

    private fun showBottomSheet() {
        showActionPickerBottomSheet(
            options = ArrayList<Option>().apply {
                add(
                    Option().setId(R.id.change_pin.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_change_pin_text))
                )
                add(
                    Option().setId(R.id.freeze_card.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_freeze_card_text))
                )
                add(
                    Option().setId(R.id.view_statement.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_view_statement_text))
                )
                add(
                    Option().setId(R.id.report_lost_card.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_report_lost_card_text))
                )
                add(
                    Option().setId(R.id.cancel.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_cancel_text))
                )
            },
            config = actionPickerConfig() {
                sheetAnimationDuration(300L)
                    .topGapSize(dimen(co.yap.R.dimen.margin_extra_small).toFloat())
            },
            onItemSelectedListener = OnItemSelectedListener {
                when (it.id) {
                    R.id.change_pin.toLong() -> toast("Change Pin")
                    R.id.freeze_card.toLong() -> toast("Freeze Card")
                    R.id.view_statement.toLong() -> {
                        launchActivity<CardStatementsActivity> {
                            putExtra("card", getDummyCard())
                            putExtra("isFromDrawer", false)
                        }
                    }
                    R.id.report_lost_card.toLong() -> {
                        startActivityForResult(
                            ReportLostOrStolenCardActivity.newIntent(
                                requireContext(),
                                getDummyCard()
                            ), Constants.REQUEST_REPORT_LOST_OR_STOLEN
                        )
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

    private fun getDummyCard(): Card {
        return Card(
            cardType = "PREPAID",
            uuid = "b4ba4040-d904-4742-96aa-374ce6ed6112",
            physical = false,
            active = false,
            cardName = "Hassnain Ali",
            status = "HOTLISTED",
            blocked = false,
            delivered = false,
            cardSerialNumber = "1000000002095",
            maskedCardNo = "5370 38** **** 7529",
            atmAllowed = true,
            onlineBankingAllowed = true,
            retailPaymentAllowed = true,
            paymentAbroadAllowed = true,
            accountType = "B2C_ACCOUNT",
            expiryDate = "11/22",
            cardBalance = "0.00",
            cardScheme = "Master Card",
            currentBalance = "0.00",
            availableBalance = "0.00",
            customerId = "3000000000112",
            accountNumber = "0188000000469",
            productCode = "CS",
            pinCreated = false,
            deliveryStatus = "ORDERED",
            shipmentStatus = null,
            nameUpdated = true,
            newPin = "",
            frontImage = "",
            backImage = ""
        )
    }
}