package co.yap.billpayments.billdetail.billaccountdetail.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemBillHistoryBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class BillHistoryViewHolder(private val layoutItemBillHistoryBinding: LayoutItemBillHistoryBinding) :
    RecyclerView.ViewHolder(layoutItemBillHistoryBinding.root) {

    fun onBind(
        billHistoryModel: BillHistoryModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemBillHistoryBinding.viewModel =
            BillHistoryViewModel(billHistoryModel, position, onItemClickListener)
        layoutItemBillHistoryBinding.executePendingBindings()
    }
}
