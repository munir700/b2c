package co.yap.billpayments.dashboard.billaccountdetail.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemBillHistoryBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter

class BillHistoryAdapter(private val list: MutableList<BillHistoryModel>) :
    BaseBindingRecyclerAdapter<BillHistoryModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return BillHistoryViewHolder(
            binding as LayoutItemBillHistoryBinding
        )
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_my_bills

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BillHistoryViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
