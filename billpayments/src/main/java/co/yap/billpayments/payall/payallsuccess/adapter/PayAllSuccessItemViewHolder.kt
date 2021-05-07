package co.yap.billpayments.payall.payallsuccess.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemPayAllSuccessBinding
import co.yap.networking.transactions.responsedtos.billpayment.PaidBill
import co.yap.yapcore.helpers.extentions.strikeThroughText
import co.yap.yapcore.interfaces.OnItemClickListener

class PayAllSuccessItemViewHolder(private val layoutItemPayAllSuccessBinding: LayoutItemPayAllSuccessBinding) :
    RecyclerView.ViewHolder(layoutItemPayAllSuccessBinding.root) {

    fun onBind(
        paidBill: PaidBill?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemPayAllSuccessBinding.tvAmount.strikeThroughText(paidBill?.PaymentStatus.equals("Declined"))
        layoutItemPayAllSuccessBinding.viewModel =
            PayAllSuccessItemViewModel(paidBill, position, onItemClickListener)
        layoutItemPayAllSuccessBinding.executePendingBindings()
    }
}
