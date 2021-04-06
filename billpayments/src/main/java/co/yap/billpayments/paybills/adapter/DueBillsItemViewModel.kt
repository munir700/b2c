package co.yap.billpayments.paybills.adapter

import android.view.View
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.interfaces.OnItemClickListener
import java.text.SimpleDateFormat

class DueBillsItemViewModel(
    val dueBill: DueBill,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun calculateIsOverDue(): Boolean {
        if (DateUtils.isDatePassed(
                dueBill.billDueDate,
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
