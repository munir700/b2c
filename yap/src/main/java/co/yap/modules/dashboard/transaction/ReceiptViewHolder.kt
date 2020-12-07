package co.yap.modules.dashboard.transaction

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemTransectionReciptBinding
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.yapcore.interfaces.OnItemClickListener

class ReceiptViewHolder(val itemReceiptBinding: ItemTransectionReciptBinding) :
    RecyclerView.ViewHolder(itemReceiptBinding.root) {
    fun onBind(
        itemReceipt: ReceiptModel, position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemReceiptBinding.viewModel =
            TransactionReceiptItemViewModel(
                itemReceipt,
                position,
                onItemClickListener
            )
        itemReceiptBinding.executePendingBindings()

    }
}
