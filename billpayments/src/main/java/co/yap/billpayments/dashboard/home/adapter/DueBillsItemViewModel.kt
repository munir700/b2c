package co.yap.billpayments.dashboard.home.adapter

import android.view.View
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.interfaces.OnItemClickListener

class DueBillsItemViewModel(
    val dueBill: ViewBillModel,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    var isBillerUnavailable: Boolean = false

    init {
        isBillerUnavailable = true
    }

    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, dueBill, position)
    }
}
