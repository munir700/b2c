package co.yap.billpayments.dashboard.home.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.dashboard.mybills.adapter.BillModel
import co.yap.billpayments.databinding.LayoutItemBillDueBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class DueBillsItemViewHolder(private val layoutItemBillDueBinding: LayoutItemBillDueBinding) :
    RecyclerView.ViewHolder(layoutItemBillDueBinding.root) {

    fun onBind(
        dueBill: BillModel,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemBillDueBinding.viewModel =
            DueBillsItemViewModel(
                dueBill,
                position,
                onItemClickListener
            )
        layoutItemBillDueBinding.executePendingBindings()
    }
}
