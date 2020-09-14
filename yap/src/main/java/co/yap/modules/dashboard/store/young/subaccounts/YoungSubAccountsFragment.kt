package co.yap.modules.dashboard.store.young.subaccounts

import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungSubaccountBinding
import co.yap.modules.subaccounts.account.card.SubAccountAdapter
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment

class YoungSubAccountsFragment:
    BaseRecyclerViewFragment<FragmentYoungSubaccountBinding, IYoungSubAccounts.State, YoungSubAccountsVM,
            SubAccountAdapter, SubAccount>(), RecyclerViewDragDropManager.OnItemDragEventListener  {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_subaccount
    override fun onItemDragMoveDistanceUpdated(offsetX: Int, offsetY: Int) {
    }
    override fun onItemDragStarted(position: Int) {
    }
    override fun onItemDragPositionChanged(
        fromPosition: Int,
        toPosition: Int,
        draggingItemHolder: RecyclerView.ViewHolder?,
        swapTargetHolder: RecyclerView.ViewHolder?
    ) {
    }
    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
    }
}