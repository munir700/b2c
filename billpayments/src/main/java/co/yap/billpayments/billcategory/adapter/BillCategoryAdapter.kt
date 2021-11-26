package co.yap.billpayments.billcategory.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ItemBillCategoryBinding
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class BillCategoryAdapter(private val list: MutableList<BillProviderModel>) :
    BaseBindingRecyclerAdapter<BillProviderModel, RecyclerView.ViewHolder>(list) {

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return BillCategoryItemViewHolder(binding as ItemBillCategoryBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_bill_category

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is BillCategoryItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
