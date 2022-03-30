package co.yap.billpayments.paybill.prepaid.skuadapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemMultiSkuBinding
import co.yap.networking.customers.responsedtos.billpayment.SkuCatalogs
import co.yap.yapcore.BaseBindingRecyclerAdapter

class SkuAdapter(private val list: MutableList<SkuCatalogs>) :
    BaseBindingRecyclerAdapter<SkuCatalogs, RecyclerView.ViewHolder>(list) {

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return SkuItemViewHolder(binding as LayoutItemMultiSkuBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_multi_sku

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is SkuItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
