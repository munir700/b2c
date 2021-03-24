package co.yap.billpayments.mybills.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.ItemMyBillsBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsItemViewHolder(private val itemMyBillsBinding: ItemMyBillsBinding) :
    RecyclerView.ViewHolder(itemMyBillsBinding.root) {

    fun onBind(
        myBillsModel: MyBillsModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemMyBillsBinding.viewModel =
            MyBillsItemViewModel(myBillsModel, position, onItemClickListener)
        itemMyBillsBinding.executePendingBindings()
    }
}
