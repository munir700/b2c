package co.yap.billpayments.paybills.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ItemMyBillsBinding
import co.yap.billpayments.databinding.LayoutItemBillDueBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter

class DueBillsAdapter(private val list: MutableList<DueBill>) :
    BaseBindingRecyclerAdapter<DueBill, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return DueBillsItemViewHolder(binding as LayoutItemBillDueBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_bill_due

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DueBillsItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
