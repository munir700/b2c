package co.yap.billpayments.paybills.adapter

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class DueBillsItemViewModel(
    val dueBill: DueBill,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, dueBill, position)
    }
}
