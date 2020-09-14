package co.yap.modules.dashboard.store.young.subaccounts

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungSubaccountBinding
import co.yap.modules.subaccounts.account.card.SubAccountAdapter
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.widgets.State
import co.yap.widgets.advrecyclerview.animator.DraggableItemAnimator
import co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager
import co.yap.widgets.advrecyclerview.utils.WrapperAdapterUtils
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment

class YoungSubAccountsFragment:
    BaseRecyclerViewFragment<FragmentYoungSubaccountBinding, IYoungSubAccounts.State, YoungSubAccountsVM,
            SubAccountAdapter, SubAccount>(), RecyclerViewDragDropManager.OnItemDragEventListener  {
    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    private var mRecyclerViewDragDropManager: RecyclerViewDragDropManager? = null

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_subaccount

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setRefreshEnabled(false)
        setHasOptionsMenu(true)
        initDragDropAdapter()
    }
    override fun handleState(state: State?) {
        super.handleState(state)
        recyclerView?.adapter = mWrappedAdapter
    }
    private fun initDragDropAdapter() {
        mRecyclerViewDragDropManager = RecyclerViewDragDropManager().apply {
            mWrappedAdapter = createWrappedAdapter(adapter)
            setInitiateOnLongPress(false)
            setInitiateOnMove(true)
            setLongPressTimeout(250)
            dragEdgeScrollSpeed = 1.0f
            dragStartItemAnimationDuration = 750
            draggingItemAlpha = 1f
            isCheckCanDropEnabled = true
            draggingItemRotation = 15.0f
            onItemDragEventListener = this@YoungSubAccountsFragment
            itemSettleBackIntoPlaceAnimationDuration = 1000
            itemMoveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT
        }
        val animator = DraggableItemAnimator()
        recyclerView?.adapter = mWrappedAdapter // requires *wrapped* adapter
        recyclerView?.itemAnimator = animator
        recyclerView?.let { mRecyclerViewDragDropManager?.attachRecyclerView(it) }
    }

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

    override fun onDestroyView() {
        mRecyclerViewDragDropManager?.let {
            it.release()
            mRecyclerViewDragDropManager = null
        }

        mWrappedAdapter?.let {
            WrapperAdapterUtils.releaseAll(it)
            mWrappedAdapter = null
        }
        super.onDestroyView()
    }

}