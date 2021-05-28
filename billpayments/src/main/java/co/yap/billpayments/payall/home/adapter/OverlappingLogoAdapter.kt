package co.yap.billpayments.payall.home.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemPayAllLogoBinding
import co.yap.networking.customers.responsedtos.billpayment.BillerInfoModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class OverlappingLogoAdapter(private val list: MutableList<BillerInfoModel>) :
    BaseBindingRecyclerAdapter<BillerInfoModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return OverlappingLogoItemViewHolder(
            binding as LayoutItemPayAllLogoBinding
        )
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_pay_all_logo
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OverlappingLogoItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
