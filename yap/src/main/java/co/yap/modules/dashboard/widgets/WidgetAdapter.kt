package co.yap.modules.dashboard.widgets


import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.BR
import co.yap.databinding.ItemWidgetAddRemoveBodyBinding
import co.yap.widgets.advrecyclerview.decoration.IStickyHeaderViewHolder
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder
import co.yap.widgets.advrecyclerview.utils.BaseExpandableRVAdapter
import co.yap.yapcore.helpers.extentions.onClick

class WidgetAdapter(
    internal var widgetData: MutableMap<String?, List<Widget>>,
    val expandableItemManager: RecyclerViewExpandableItemManager
) :
    BaseExpandableRVAdapter<Widget, WidgetChildItemVM, WidgetAdapter.ChildViewHolder, WidgetList, WidgetGroupItemVM, WidgetAdapter.GroupViewHolder>() {
    var onItemClick: ((view: View, groupPosition: Int, childPosition: Int, data: Widget?) -> Unit)? =
        null

    init {
        setHasStableIds(true)
    }

    fun setData(widgetList: MutableMap<String?, List<Widget>>?) {
        widgetList?.let {
            this.widgetData = it
            notifyDataSetChanged()
        } ?: run {
            this.widgetData = mutableMapOf()
            notifyDataSetChanged()
        }
    }

    override fun getInitialGroupExpandedState(groupPosition: Int) = false

    override fun getChildViewHolder(
        view: View,
        viewModel: WidgetChildItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = ChildViewHolder(view, viewModel, mDataBinding)

    override fun getGroupViewHolder(
        view: View,
        viewModel: WidgetGroupItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = GroupViewHolder(view, viewModel, mDataBinding)

    override fun onBindGroupViewHolder(
        holder: GroupViewHolder,
        groupPosition: Int,
        viewType: Int
    ) {
        val widget =
            widgetData[widgetData.keys.toList()[groupPosition]] ?: mutableListOf()
        val groupData =
            WidgetList(widget = widget)
        holder.setItem(groupData, groupPosition)
    }

    override fun onBindChildViewHolder(
        holder: ChildViewHolder,
        groupPosition: Int,
        childPosition: Int,
        viewType: Int
    ) {
        val widget =
            widgetData[widgetData.keys.toList()[groupPosition]]?.get(childPosition)
        widget?.let {
            holder.setItem(it, childPosition)
        }
        holder.onClick { view, _, _ ->
            onItemClick?.invoke(view, groupPosition, childPosition, widget)
        }

    }

    override fun getChildLayoutId(viewType: Int) = getChildViewModel(viewType).layoutRes()
    override fun getGroupLayoutId(viewType: Int) = getGroupViewModel(viewType).layoutRes()
    override fun getGroupViewModel(viewType: Int) = WidgetGroupItemVM()
    override fun getGroupVariableId() = BR.widgetGroupItemVM
    override fun getChildViewModel(viewType: Int) = WidgetChildItemVM()
    override fun getChildVariableId() = BR.widgetChildItemVM
    override fun getGroupCount() = widgetData.size
    override fun getChildCount(groupPosition: Int) =
        widgetData[widgetData.keys.toList()[groupPosition]]?.size ?: 0

    override fun getGroupId(groupPosition: Int) = groupPosition.plus(1L)
    override fun getChildId(groupPosition: Int, childPosition: Int) =
        widgetData[widgetData.keys.toList()[groupPosition]]?.get(childPosition)?.id?.toLong()
            ?: childPosition.plus(groupPosition).toLong()

    override fun getGroupItemViewType(groupPosition: Int) = groupPosition
    override fun getChildItemViewType(groupPosition: Int, childPosition: Int): Int {
        return widgetData[widgetData.keys.toList()[groupPosition]]?.get(childPosition)?.id
            ?: childPosition.plus(groupPosition)
    }


    class GroupViewHolder(
        view: View,
        viewModel: WidgetGroupItemVM,
        mDataBinding: ViewDataBinding
    ) : AbstractExpandableItemViewHolder<WidgetList, WidgetGroupItemVM>(
        view,
        viewModel,
        mDataBinding
    ), IStickyHeaderViewHolder

    inner class ChildViewHolder(
        view: View,
        viewModel: WidgetChildItemVM,
        mDataBinding: ViewDataBinding
    ) : AbstractExpandableItemViewHolder<Widget, WidgetChildItemVM>(
        view,
        viewModel,
        mDataBinding
    ) {
        private var widget: Widget? = null
        private var mPosition: Int? = null
        private var binding: ItemWidgetAddRemoveBodyBinding =
            mDataBinding as (ItemWidgetAddRemoveBodyBinding)

        override fun setItem(item: Widget, position: Int) {
            super.setItem(item, position)
            widget = item
        }

    }

}
