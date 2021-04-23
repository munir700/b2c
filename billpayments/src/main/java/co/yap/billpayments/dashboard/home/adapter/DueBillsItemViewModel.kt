package co.yap.billpayments.dashboard.home.adapter

import android.view.View
import co.yap.billpayments.dashboard.mybills.adapter.BillModel
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.interfaces.OnItemClickListener
import java.text.SimpleDateFormat

class DueBillsItemViewModel(
    val dueBill: BillModel,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun calculateIsOverDue(): Boolean {
        if (DateUtils.isDatePassed(
                dueBill.dueDate.toString(),
                SimpleDateFormat(DateUtils.FORMATE_DATE_MONTH_YEAR)
            )
        ) {
            return true
        }
        return false
    }

    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, dueBill, position)
    }
}
