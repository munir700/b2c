package co.yap.billpayments.payall.payallsuccess.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemPayAllSuccessBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter

class PayAllBillsAdapter(private val list: MutableList<PaidBill>) :
    BaseBindingRecyclerAdapter<PaidBill, RecyclerView.ViewHolder>(list) {

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return PayAllStatusItemViewHolder(binding as LayoutItemPayAllSuccessBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_pay_all_success

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is PayAllStatusItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
