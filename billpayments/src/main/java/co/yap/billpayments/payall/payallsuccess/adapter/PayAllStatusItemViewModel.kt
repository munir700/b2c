package co.yap.billpayments.payall.payallsuccess.adapter

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class PayAllStatusItemViewModel(
    val paidBill: PaidBill?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, paidBill!!, position)
    }
}
