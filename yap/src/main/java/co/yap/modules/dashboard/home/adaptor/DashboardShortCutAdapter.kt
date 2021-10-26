package co.yap.modules.dashboard.home.adaptor

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemDashboardShortcutsBindingImpl
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class DashboardShortCutAdapter(
    private val list: MutableList<String>,
    private val adaptorClick: OnItemClickListener
) :
    BaseBindingRecyclerAdapter<String, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_dashboard_shortcuts

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return ShortCutViewHolder(binding as ItemDashboardShortcutsBindingImpl)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is ShortCutViewHolder) {
            holder.onBind(list[position], adaptorClick, position)
        }
    }

    class ShortCutViewHolder(private val itemDashboardShortcutsBindingImpl: ItemDashboardShortcutsBindingImpl) :
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