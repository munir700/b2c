package co.yap.billpayments.billers.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.ItemBillersBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class BillerItemViewHolder(private val itemBillersBinding: ItemBillersBinding) :
    RecyclerView.ViewHolder(itemBillersBinding.root) {

    fun onBind(
        billerModel: BillerModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemBillersBinding.viewModel =
            BillerItemViewModel(billerModel, position, onItemClickListener)
        itemBillersBinding.executePendingBindings()
    }
}
