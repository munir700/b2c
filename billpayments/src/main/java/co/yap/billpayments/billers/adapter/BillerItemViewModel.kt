package co.yap.billpayments.billers.adapter

import android.view.View
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BillerItemViewModel(
    val billerCatalogModel: BillerCatalogModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billerCatalogModel!!, position)
    }
}
