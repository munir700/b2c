package co.yap.modules.dashboard.yapit.y2y.home.yapcontacts

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemContactsBinding
import co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.YapContactItemViewHolder
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.BaseBindingRecyclerAdapter

class YapContactsAdaptor(context: Context, private val list: MutableList<Contact>) :
    BaseBindingRecyclerAdapter<Contact, RecyclerView.ViewHolder>(list) {
    private val contactColors = intArrayOf(
        R.drawable.bg_round_light_red,
        R.drawable.bg_round_light_blue,
        R.drawable.bg_round_light_green,
        R.drawable.bg_round_light_orange
    )

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_contacts

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return YapContactItemViewHolder(binding as ItemContactsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is YapContactItemViewHolder) {
            holder.onBind(contactColors, list[position], position, onItemClickListener)
        }
    }

}
