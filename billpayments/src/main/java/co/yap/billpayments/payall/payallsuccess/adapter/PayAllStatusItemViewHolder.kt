package co.yap.billpayments.payall.payallsuccess.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemPayAllSuccessBinding
import co.yap.yapcore.enums.BillPaymentStatus
import co.yap.yapcore.helpers.extentions.strikeThroughText
import co.yap.yapcore.interfaces.OnItemClickListener

class PayAllStatusItemViewHolder(private val layoutItemPayAllSuccessBinding: LayoutItemPayAllSuccessBinding) :
    RecyclerView.ViewHolder(layoutItemPayAllSuccessBinding.root) {
    fun onBind(
        paidBill: PaidBill?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        if (paidBill?.paymentStatus.equals(
                BillPaymentStatus.FAILEDTITLE.title
            ) || paidBill?.paymentStatus.equals(
                BillPaymentStatus.IN_PROGRESSTITLE.title
            )
        ) {
            layoutItemPayAllSuccessBinding.tvAmount.strikeThroughText(
                true
            )
        } else {
            layoutItemPayAllSuccessBinding.tvAmount.strikeThroughText(
                false
            )
        }

        layoutItemPayAllSuccessBinding.viewModel =
            PayAllStatusItemViewModel(paidBill, position, onItemClickListener)
        layoutItemPayAllSuccessBinding.executePendingBindings()
    }
}
