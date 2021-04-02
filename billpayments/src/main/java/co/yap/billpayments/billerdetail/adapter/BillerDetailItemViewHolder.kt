package co.yap.billpayments.billerdetail.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemBillerDetailBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class BillerDetailItemViewHolder(private val layoutItemBillerDetailBinding: LayoutItemBillerDetailBinding) :
    RecyclerView.ViewHolder(layoutItemBillerDetailBinding.root) {

    fun onBind(
        billerDetailInputFieldModel: BillerDetailInputFieldModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemBillerDetailBinding.viewModel =
            BillerDetailItemViewModel(billerDetailInputFieldModel, position, onItemClickListener)
        layoutItemBillerDetailBinding.executePendingBindings()
    }
}
