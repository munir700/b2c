package co.yap.household.dashboard.cards

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentMyCardBinding
import co.yap.widgets.DividerItemDecoration
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.extentions.dimen

class MyCardFragment :
    BaseRecyclerViewFragment<FragmentMyCardBinding, IMyCard.State, MyCardVM, MyCardFragment.Adapter, TransactionModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_my_card
    override fun toolBarVisibility(): Boolean? = true

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

    class Adapter(mValue: MutableList<TransactionModel>, navigation: NavController?) :
        BaseRVAdapter<TransactionModel, MyCardRecentTransactionsItemVM, BaseViewHolder<TransactionModel, MyCardRecentTransactionsItemVM>>(
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
        override fun getItemCount(): Int = 10

    }
}