package co.yap.billpayments.billers.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ItemBillersBinding
import co.yap.yapcore.BaseBindingSearchRecylerAdapter

class BillersAdapter(private val list: MutableList<BillerModel>) :
    BaseBindingSearchRecylerAdapter<BillerModel, RecyclerView.ViewHolder>(list) {

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return BillerItemViewHolder(binding as ItemBillersBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_billers

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is BillerItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }

    override fun filterItem(constraint: CharSequence?, item: BillerModel): Boolean {
        val filterString = constraint.toString().toLowerCase()
        val name = item.name?.toLowerCase()
        return name.contains(filterString)
    }
}
