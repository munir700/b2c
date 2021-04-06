package co.yap.billpayments.mybills.adapter

import android.view.View
import co.yap.networking.customers.responsedtos.billpayment.BillModel
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsItemViewModel(
    val billModel: BillModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billModel!!, position)
    }
}
