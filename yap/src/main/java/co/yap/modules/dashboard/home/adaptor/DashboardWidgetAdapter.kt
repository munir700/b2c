package co.yap.modules.dashboard.home.adaptor

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemDashboardWidgetBindingImpl
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class DashboardWidgetAdapter(
    private val list: MutableList<String>,
    private val adaptorClick: OnItemClickListener
) :
    BaseBindingRecyclerAdapter<String, RecyclerView.ViewHolder>(list) {

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
            data: String,
            adaptorClick: OnItemClickListener,
            groupPosition: Int
        ) {
            itemDashboardShortcutsBindingImpl.executePendingBindings()
        }
    }
}