package co.yap.billpayments.addbiller.addbillerdetail.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemBillerDetailBinding
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.interfaces.OnItemClickListener

class AddBillerDetailItemViewHolder(private val layoutItemBillerDetailBinding: LayoutItemBillerDetailBinding) :
    RecyclerView.ViewHolder(layoutItemBillerDetailBinding.root) {

    fun onBind(
        addBillerDetailInputFieldModel: AddBillerDetailInputFieldModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemBillerDetailBinding.viewModel =
            AddBillerDetailItemViewModel(
                addBillerDetailInputFieldModel,
                position,
                onItemClickListener
            )
        layoutItemBillerDetailBinding.etUserInput.afterTextChanged {
            onItemClickListener?.onItemClick(layoutItemBillerDetailBinding.etUserInput, it, -1)
        }
        layoutItemBillerDetailBinding.executePendingBindings()
    }
}
