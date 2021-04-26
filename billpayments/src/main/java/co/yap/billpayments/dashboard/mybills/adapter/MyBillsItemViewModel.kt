package co.yap.billpayments.dashboard.mybills.adapter

import android.view.View
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsItemViewModel(
    val billModel: ViewBillModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billModel!!, position)
    }

    val formattedBillStatus: String
        get() {
            return (when (billModel?.status) {
                BillStatus.BILL_DUE.name -> {
                    return BillStatus.BILL_DUE.title
                }
                BillStatus.PAID.name -> {
                    return BillStatus.PAID.title
                }
                BillStatus.OVERDUE.name -> {
                    return BillStatus.OVERDUE.title
                }
                else -> BillStatus.PAID.title
            })
        }
}
