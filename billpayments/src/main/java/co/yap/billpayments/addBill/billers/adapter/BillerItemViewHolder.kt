package co.yap.billpayments.addBill.billers.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemBillerBinding
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BillerItemViewHolder(private val layoutItemBillerBinding: LayoutItemBillerBinding) :
    RecyclerView.ViewHolder(layoutItemBillerBinding.root) {

    fun onBind(
        billerCatalogModel: BillerCatalogModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemBillerBinding.viewModel =
            BillerItemViewModel(billerCatalogModel, position, onItemClickListener)
        layoutItemBillerBinding.executePendingBindings()
    }
}
