package co.yap.modules.dashboard.widgets

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemWidgetAddRemoveBodyBinding
import co.yap.databinding.ItemWidgetAddRemoveHeaderBinding
import co.yap.modules.dashboard.home.models.WidgetItemViewModel
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
            itemWidgetAddRemoveBodyBinding.viewModel =
                WidgetItemViewModel(data, groupPosition, adaptorClick)
            itemWidgetAddRemoveBodyBinding.executePendingBindings()
        }
    }
}
