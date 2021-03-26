package co.yap.billpayments.billcategory.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.ItemBillCategoryBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class BillCategoryItemViewHolder(private val itemBillCategoryBinding: ItemBillCategoryBinding) :
    RecyclerView.ViewHolder(itemBillCategoryBinding.root) {

    fun onBind(
        billCategoryModel: BillCategoryModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemBillCategoryBinding.viewModel =
            BillCategoryItemViewModel(billCategoryModel, position, onItemClickListener)
        itemBillCategoryBinding.executePendingBindings()
    }
}
