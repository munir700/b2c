package co.yap.modules.dashboard.widgets

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemWidgetAddRemoveBodyBinding
import co.yap.databinding.ItemWidgetAddRemoveHeaderBinding
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class WidgetAdapter(

    internal var list: MutableList<WidgetData>,

    private val adaptorClick: OnItemClickListener

) :

    BaseBindingRecyclerAdapter<WidgetData, RecyclerView.ViewHolder>(list) {
    private val heading = 1
    private val actual = 2

    override fun getLayoutIdForViewType(viewType: Int): Int =
        if (viewType == actual) R.layout.item_widget_add_remove_body else R.layout.item_widget_add_remove_header

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return if (binding is ItemWidgetAddRemoveBodyBinding) BodyViewHolder(
            binding
        ) else HeaderViewHolder(
            binding as ItemWidgetAddRemoveHeaderBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is BodyViewHolder) {
            holder.onBind(list[position], adaptorClick, position)
        } else {
            if (holder is HeaderViewHolder)
                holder.onBind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].id == -1) heading else actual
    }

    override fun getItemCount() = list.size

    fun setData(listData: MutableList<WidgetData>?) {
        listData?.let {
            this.list = listData
            notifyDataSetChanged()
        }
    }

    class HeaderViewHolder(private val itemWidgetAddRemoveHeaderBinding: ItemWidgetAddRemoveHeaderBinding) :
        RecyclerView.ViewHolder(itemWidgetAddRemoveHeaderBinding.root) {

        fun onBind(position: Int) {
            itemWidgetAddRemoveHeaderBinding.executePendingBindings()
        }
    }

    class BodyViewHolder(private val itemWidgetAddRemoveBodyBinding: ItemWidgetAddRemoveBodyBinding) :
        RecyclerView.ViewHolder(itemWidgetAddRemoveBodyBinding.root) {

        fun onBind(
            data: WidgetData,
            adaptorClick: OnItemClickListener,
            groupPosition: Int
        ) {
//            itemWidgetAddRemoveBodyBinding.viewModel =
//                WidgetItemViewModel(data, groupPosition, adaptorClick)
//            itemWidgetAddRemoveBodyBinding.executePendingBindings()
        }
    } }

//import android.view.View
//import androidx.databinding.ViewDataBinding
//import androidx.navigation.NavController
//import androidx.recyclerview.widget.RecyclerView
//import co.yap.R
//import co.yap.networking.customers.models.dashboardwidget.WidgetData
//import co.yap.widgets.advrecyclerview.draggable.DraggableItemAdapter
//import co.yap.widgets.advrecyclerview.swipeable.SwipeableItemAdapter
//import co.yap.widgets.advrecyclerview.swipeable.SwipeableItemConstants.*
//import co.yap.widgets.advrecyclerview.swipeable.action.SwipeResultAction
//import co.yap.widgets.advrecyclerview.swipeable.action.SwipeResultActionDefault
//import co.yap.widgets.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection
//import co.yap.widgets.advrecyclerview.utils.AbstractDraggableSwipeableItemViewHolder
//import co.yap.yapcore.BaseRVAdapter
//import java.util.*
//
//class WidgetAdapter(mValue: MutableList<WidgetData>, navigation: NavController?) :
//    BaseRVAdapter<WidgetData, WidgetItemViewModel, WidgetAdapter.ViewHolder>(
//        mValue,
//        navigation
//    ), DraggableItemAdapter<WidgetAdapter.ViewHolder>,
//    SwipeableItemAdapter<WidgetAdapter.ViewHolder> {
//    var editWallet: Boolean = false
//        set(value) {
//            field = value
//            if (oldSwipePosition != RecyclerView.NO_POSITION) {
//                datas[oldSwipePosition].isPinned = false
//            }
//            oldSwipePosition = RecyclerView.NO_POSITION
//            notifyDataSetChanged()
//        }
//    private var mEventListener: EventListener? = null
//    private var oldSwipePosition: Int = RecyclerView.NO_POSITION
//
//    init {
//        setHasStableIds(true)
//    }
//
//    override fun getItemId(position: Int) = datas[position].id!!
//    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        super.onBindViewHolder(holder, position)
//        holder.binding.ivArrow.setImageResource(if (editWallet) R.drawable.ic_hamburger else R.drawable.ic_back_arrow_left)
//        // set swiping properties
//        holder.maxLeftSwipeAmount = -0.2f
//        holder.maxRightSwipeAmount = 0f
//        holder.swipeItemHorizontalSlideAmount = if (datas[position].isPinned == true) -0.2f else 0f
//    }
//
//    override fun getViewHolder(
//        view: View,
//        viewModel: WidgetItemViewModel,
//        mDataBinding: ViewDataBinding,
//        viewType: Int
//    ) = ViewHolder(this, view, viewModel, mDataBinding)
//
//    override fun getViewModel(viewType: Int) = WidgetItemViewModel()
//    override fun getVariableId() = BR.viewModel
//    override fun onCheckCanStartDrag(holder: ViewHolder, position: Int, x: Int, y: Int) = editWallet
//    override fun onGetItemDraggableRange(holder: ViewHolder, position: Int): Nothing? = null
//    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
//        if (fromPosition == toPosition) {
//            return
//        }
//        Collections.swap(datas, fromPosition, toPosition)
////        val item: MultiCurrencyWallet =
////            datas.removeAt(fromPosition)
////        datas.add(toPosition, item)
//    }
//
//    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int) = true
//
//    override fun onItemDragStarted(position: Int) {
//        notifyDataSetChanged()
//    }
//
//    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
//        notifyDataSetChanged()
//    }
//
//    fun setEventListener(eventListener: EventListener) {
//        mEventListener = eventListener
//    }
//
//
//    override fun onGetSwipeReactionType(holder: ViewHolder, position: Int, x: Int, y: Int): Int {
//        return if (onCheckCanStartDrag(holder, position, x, y)) {
//            REACTION_CAN_NOT_SWIPE_BOTH_H
//        } else {
//            REACTION_CAN_SWIPE_LEFT
//        }
//    }
//
//    override fun onSwipeItemStarted(holder: ViewHolder, position: Int) {
//        notifyDataSetChanged()
//    }
//
//    override fun onSetSwipeBackground(holder: ViewHolder, position: Int, type: Int) {
//        if (type == DRAWABLE_SWIPE_NEUTRAL_BACKGROUND) {
//            holder.binding.flSwipeable.visibility = View.GONE
//        } else {
//            holder.binding.flSwipeable.visibility = View.VISIBLE
//
//        }
//    }
//
//    override fun onSwipeItem(holder: ViewHolder, position: Int, result: Int): SwipeResultAction {
//        return when (result) {
//            RESULT_SWIPED_LEFT -> {
//                SwipeLeftResultAction(
//                    this,
//                    position
//                )
//            }
//            else -> UnpinResultActionconstructor(this, position)
//        }
//    }
//
//    override fun removeAt(position: Int) {
//        super.removeAt(position)
//        oldSwipePosition = RecyclerView.NO_POSITION
//    }
//
//    class ViewHolder(
//        mAdapter: WidgetAdapter?,
//        view: View,
//        viewModel: WidgetItemViewModel,
//        mDataBinding: ViewDataBinding
//    ) :
//        AbstractDraggableSwipeableItemViewHolder<MultiCurrencyWallet, WidgetItemViewModel>(
//            view,
//            viewModel,
//            mDataBinding
//        ) {
//        var binding = mDataBinding as ItemWalletV2Binding
//        override fun getSwipeableContainerView() = binding.foregroundContainer
//
//        init {
//            binding.tvDelete.setOnClickListener {
//                mAdapter?.mEventListener?.onUnderSwipeableViewButtonClicked(
//                    itemView,
//                    adapterPosition
//                )
//            }
//        }
//    }
//
//    internal class SwipeLeftResultAction constructor(
//        private var mAdapter: WidgetAdapter?,
//        private val position: Int
//    ) :
//        SwipeResultActionMoveToSwipedDirection() {
//        private var mSetPinned = false
//        override fun onPerformAction() {
//            super.onPerformAction()
//            mAdapter?.datas?.get(position)?.isPinned?.let {
//                if (!it) {
//                    mAdapter?.datas?.get(position)?.isPinned = true
//                    mAdapter?.notifyItemChanged(position)
//                    mSetPinned = true
//                }
//            }
//            if (mAdapter?.oldSwipePosition != RecyclerView.NO_POSITION)
//                mAdapter?.datas?.get(mAdapter?.oldSwipePosition!!)?.isPinned?.let {
//                    if (it) {
//                        mAdapter?.datas?.get(mAdapter?.oldSwipePosition!!)?.isPinned = false
//                        mAdapter?.notifyItemChanged(mAdapter?.oldSwipePosition!!)
//                    }
//                }
//        }
//
//        override fun onSlideAnimationEnd() {
//            super.onSlideAnimationEnd()
//            mAdapter?.oldSwipePosition = position
//            if (mSetPinned) {
//                mAdapter?.mEventListener?.onItemPinned(position, mSetPinned)
//            }
//        }
//
//        override fun onCleanUp() {
//            super.onCleanUp()
//            mAdapter = null
//        }
//    }
//
//    internal class UnpinResultActionconstructor(
//        private var mAdapter: WidgetAdapter?,
//        private val position: Int
//    ) :
//        SwipeResultActionDefault() {
//
//        override fun onPerformAction() {
//            super.onPerformAction()
//            mAdapter?.datas?.get(position)?.isPinned?.let {
//                if (it) {
//                    mAdapter?.datas?.get(position)?.isPinned = false
//                    mAdapter?.notifyItemChanged(position)
//                }
//            }
//        }
//
//        override fun onSlideAnimationEnd() {
//            super.onSlideAnimationEnd()
//            mAdapter?.mEventListener?.onItemPinned(position, false)
//            mAdapter?.oldSwipePosition = RecyclerView.NO_POSITION
//        }
//
//        override fun onCleanUp() {
//            super.onCleanUp()
//            mAdapter = null
//        }
//    }
//
//    interface EventListener {
//        fun onItemPinned(position: Int, isPinned: Boolean)
//        fun onUnderSwipeableViewButtonClicked(v: View?, position: Int)
//    }
//}