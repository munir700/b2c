package co.yap.billpayments.dashboard.mybills.adapter

import android.view.View
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsItemViewModel(
    val billModel: ViewBillModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billModel!!, position)
    }
}
