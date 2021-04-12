package co.yap.billpayments.addBill.billerdetail.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemBillerDetailBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter

class BillerDetailAdapter(private val list: MutableList<BillerDetailInputFieldModel>) :
    BaseBindingRecyclerAdapter<BillerDetailInputFieldModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return BillerDetailItemViewHolder(binding as LayoutItemBillerDetailBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_biller_detail

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BillerDetailItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
