package co.yap.household.dashboard.cards

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentMyCardBinding
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.widgets.DividerItemDecoration
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.extentions.dimen
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(co.yap.R.menu.menu_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showActionPickerBottomSheet(
            options = ArrayList<Option>().apply {
                add(Option().setId(R.id.change_pin.toLong()).setTitle(getString(Strings.screen_household_my_card_screen_menu_change_pin_text)))
                add(Option().setId(R.id.freeze_card.toLong()).setTitle(getString(Strings.screen_household_my_card_screen_menu_freeze_card_text)))
                add(Option().setId(R.id.view_statement.toLong()).setTitle(getString(Strings.screen_household_my_card_screen_menu_view_statement_text)))
                add(Option().setId(R.id.report_lost_card.toLong()).setTitle(getString(Strings.screen_household_my_card_screen_menu_report_lost_card_text)))
                add(Option().setId(R.id.cancel.toLong()).setTitle(getString(Strings.screen_household_my_card_screen_menu_cancel_text)))
            },
            config = actionPickerConfig() {
                sheetAnimationDuration(300L)
                    .topGapSize(dimen(co.yap.R.dimen.margin_extra_small).toFloat())
            },
            onItemSelectedListener = OnItemSelectedListener {
                when (it.id) {
                    R.id.change_pin.toLong() -> toast("Change Pin")  // TODO to be changed
                    R.id.freeze_card.toLong() -> toast("Freeze Card") // TODO to be changed
                    R.id.view_statement.toLong() -> toast("View Statement") // TODO to be changed
                    R.id.report_lost_card.toLong() -> toast("Report Lost Card") // TODO to be changed
                    R.id.cancel.toLong() -> toast("Cancel") // TODO to be changed
                }
            }
        )
        return super.onOptionsItemSelected(item)
    }

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