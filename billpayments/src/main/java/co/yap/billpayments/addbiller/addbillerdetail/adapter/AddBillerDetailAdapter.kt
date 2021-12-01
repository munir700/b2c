package co.yap.billpayments.addbiller.addbillerdetail.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemBillerDetailBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter

class AddBillerDetailAdapter(private val list: MutableList<AddBillerDetailInputFieldModel>) :
    BaseBindingRecyclerAdapter<AddBillerDetailInputFieldModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return AddBillerDetailItemViewHolder(binding as LayoutItemBillerDetailBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_biller_detail

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AddBillerDetailItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
