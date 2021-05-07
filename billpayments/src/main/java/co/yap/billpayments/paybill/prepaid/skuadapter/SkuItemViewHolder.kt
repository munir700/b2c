package co.yap.billpayments.paybill.prepaid.skuadapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemMultiSkuBinding
import co.yap.networking.customers.responsedtos.billpayment.SkuCatalogs
import co.yap.yapcore.interfaces.OnItemClickListener

class SkuItemViewHolder(private val layoutItemMultiSkuBinding: LayoutItemMultiSkuBinding) :
    RecyclerView.ViewHolder(layoutItemMultiSkuBinding.root) {

    fun onBind(
        skuCatalogs: SkuCatalogs?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemMultiSkuBinding.viewModel =
            SkuItemViewModel(skuCatalogs, position, onItemClickListener)
        layoutItemMultiSkuBinding.executePendingBindings()
    }
}
