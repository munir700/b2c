package co.yap.billpayments.dashboard.home.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemBillDueBinding
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class DueBillsAdapter(private val list: MutableList<ViewBillModel>) :
    BaseBindingRecyclerAdapter<ViewBillModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return DueBillsItemViewHolder(
            binding as LayoutItemBillDueBinding
        )
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_bill_due

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DueBillsItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
