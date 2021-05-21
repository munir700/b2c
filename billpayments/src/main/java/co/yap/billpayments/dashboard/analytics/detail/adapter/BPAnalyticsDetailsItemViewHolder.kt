package co.yap.billpayments.dashboard.analytics.detail.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemPaidBillBinding
import co.yap.networking.transactions.responsedtos.billpayments.AnalyticsBill
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.interfaces.OnItemClickListener

class BPAnalyticsDetailsItemViewHolder(private val layoutItemPaidBillBinding: LayoutItemPaidBillBinding) :
    RecyclerView.ViewHolder(layoutItemPaidBillBinding.root) {

    fun onBind(
        bill: AnalyticsBill,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {

        val timeFormatting = DateUtils.reformatStringDate(
            bill.billDate ?: "",
            DateUtils.SERVER_DATE_FORMAT,
            DateUtils.FORMAT_TIME_24H
        )
        val dateFormatting = DateUtils.reformatStringDate(
            date = bill.billDate ?: "",
            inputFormatter = DateUtils.SERVER_DATE_FORMAT,
            outFormatter = DateUtils.FORMATE_MONTH_DAY
        )

        layoutItemPaidBillBinding.dateTime = "$timeFormatting \u2022 $dateFormatting"

        layoutItemPaidBillBinding.viewModel =
            BPAnalyticsDetailsItemViewModel(
                bill,
                position,
                onItemClickListener
            )
        layoutItemPaidBillBinding.executePendingBindings()
    }
}