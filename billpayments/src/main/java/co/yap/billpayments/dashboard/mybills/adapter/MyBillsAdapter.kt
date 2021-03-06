package co.yap.billpayments.dashboard.mybills.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemMyBillsBinding
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class MyBillsAdapter(private val list: MutableList<ViewBillModel>) :
    BaseBindingRecyclerAdapter<ViewBillModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return MyBillsItemViewHolder(
            binding as LayoutItemMyBillsBinding
        )
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_my_bills

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyBillsItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
