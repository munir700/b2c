package co.yap.modules.dashboard.transaction

import androidx.databinding.ViewDataBinding
import co.yap.R
import co.yap.databinding.ItemTransectionReciptBinding
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class TransactionReceiptAdapter(
    val listItems: MutableList<ReceiptModel>
) :
    BaseBindingRecyclerAdapter<ReceiptModel, ReceiptViewHolder>(listItems) {
    override fun onCreateViewHolder(binding: ViewDataBinding): ReceiptViewHolder {
        return ReceiptViewHolder(binding as ItemTransectionReciptBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transection_recipt
    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(listItems[position], position, onItemClickListener)
    }
}