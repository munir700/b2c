package co.yap.modules.subaccounts.account.card

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.widgets.advrecyclerview.draggable.DraggableItemAdapter
import co.yap.widgets.advrecyclerview.draggable.DraggableItemState
import co.yap.widgets.advrecyclerview.draggable.DraggableItemViewHolder
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import javax.inject.Inject

class SubAccountAdapter @Inject constructor(mValue: MutableList<SubAccount>, navigation: NavController?) :
    BaseRVAdapter<SubAccount, SubAccountCardItemVM, BaseViewHolder<SubAccount, SubAccountCardItemVM>>(
        mValue, navigation
    ),
    DraggableItemAdapter<SubAccountAdapter.ViewHolder> {
    init {
        setHasStableIds(true)
    }

    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: SubAccountCardItemVM,
        mDataBinding: ViewDataBinding, viewType: Int
    ) = ViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int) = SubAccountCardItemVM()
    override fun getVariableId() = BR.subAccountCardItemVm
    class ViewHolder(
        view: View,
        viewModel: SubAccountCardItemVM,
        mDataBinding: ViewDataBinding
    ) :
        BaseViewHolder<SubAccount, SubAccountCardItemVM>(view, viewModel, mDataBinding),
        DraggableItemViewHolder {
        private val mDragState = DraggableItemState()
        override fun setDragStateFlags(flags: Int) {
            mDragState.flags = flags
        }

        override fun getDragStateFlags() = mDragState.flags
        override fun getDragState() = mDragState
    }

    override fun onCheckCanStartDrag(holder: ViewHolder, position: Int, x: Int, y: Int) = position==0
    override fun onGetItemDraggableRange(holder: ViewHolder, position: Int) = null

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
//        Timber.d(
//            "onMoveItem",
//            "onMoveItem(fromPosition = $fromPosition, toPosition = $toPosition)"
//        )
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int) = true

    override fun onItemDragStarted(position: Int) {
        notifyDataSetChanged()
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        notifyDataSetChanged()
    }
}