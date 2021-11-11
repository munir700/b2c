package co.yap.billpayments.billcategory.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.ItemBillCategoryBinding
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BillCategoryItemViewHolder(private val itemBillCategoryBinding: ItemBillCategoryBinding) :
    RecyclerView.ViewHolder(itemBillCategoryBinding.root) {

    fun onBind(
        billProviderModel: BillProviderModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemBillCategoryBinding.viewModel =
            BillCategoryItemViewModel(billProviderModel, position, onItemClickListener)
        itemBillCategoryBinding.executePendingBindings()
    }
}