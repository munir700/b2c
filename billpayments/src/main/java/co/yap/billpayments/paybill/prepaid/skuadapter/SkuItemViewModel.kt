package co.yap.billpayments.paybill.prepaid.skuadapter

import android.view.View
import co.yap.networking.customers.responsedtos.billpayment.SkuCatalogs
import co.yap.yapcore.interfaces.OnItemClickListener

class SkuItemViewModel(
    val skuCatalogs: SkuCatalogs?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, skuCatalogs!!, position)
    }
}
