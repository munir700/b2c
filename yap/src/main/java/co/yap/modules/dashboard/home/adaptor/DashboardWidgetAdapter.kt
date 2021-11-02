package co.yap.modules.dashboard.home.adaptor

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemDashboardWidgetBindingImpl
import co.yap.modules.dashboard.home.models.WidgetItemViewModel
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class DashboardWidgetAdapter(
    private val list: MutableList<WidgetData>,
    private val adaptorClick: OnItemClickListener
) :
    BaseBindingRecyclerAdapter<WidgetData, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_dashboard_widget

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return WidgetViewHolder(binding as ItemDashboardWidgetBindingImpl)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is WidgetViewHolder) {
            holder.onBind(list[position], adaptorClick, position)
        }
    }

    class WidgetViewHolder(private val itemDashboardShortcutsBindingImpl: ItemDashboardWidgetBindingImpl) :
        RecyclerView.ViewHolder(itemDashboardShortcutsBindingImpl.root) {

        fun onBind(
            data: WidgetData,
            adaptorClick: OnItemClickListener,
            position: Int
        ) {
            itemDashboardShortcutsBindingImpl.viewModel =
                WidgetItemViewModel(data, position, adaptorClick)
            itemDashboardShortcutsBindingImpl.executePendingBindings()
        }
    }
}