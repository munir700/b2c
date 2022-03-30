package co.yap.billpayments.billdetail.billaccountdetail.adapter

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class BillHistoryViewModel(
    val billHistoryModel: BillHistoryModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billHistoryModel!!, position)
    }
}
