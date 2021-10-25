package co.yap.billpayments.billcategory.adapter

import android.view.View
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BillCategoryItemViewModel(
    val billProviderModel: BillProviderModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billProviderModel!!, position)
    }
}
