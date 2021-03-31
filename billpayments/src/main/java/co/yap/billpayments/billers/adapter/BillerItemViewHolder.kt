package co.yap.billpayments.billers.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemBillerBinding
import co.yap.networking.customers.responsedtos.billpayment.BillerModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BillerItemViewHolder(private val layoutItemBillerBinding: LayoutItemBillerBinding) :
    RecyclerView.ViewHolder(layoutItemBillerBinding.root) {

    fun onBind(
        billerModel: BillerModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemBillerBinding.viewModel =
            BillerItemViewModel(billerModel, position, onItemClickListener)
        layoutItemBillerBinding.executePendingBindings()
    }
}
