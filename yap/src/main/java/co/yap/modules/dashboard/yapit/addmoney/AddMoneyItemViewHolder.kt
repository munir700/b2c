package co.yap.modules.dashboard.yapit.addmoney

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapItAddMoneyBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class AddMoneyItemViewHolder (private val itemYapItAddMoneyBinding: ItemYapItAddMoneyBinding) :
RecyclerView.ViewHolder(itemYapItAddMoneyBinding.root) {

    fun onBind(
        position: Int,
        addMoneyOptions: AddMoneyOptions,
        dimensions: IntArray,
        onItemClickListener: OnItemClickListener?
    ) {
        itemYapItAddMoneyBinding.viewModel =
            YapItAddMoneyItemViewModel(addMoneyOptions, position, onItemClickListener)
        itemYapItAddMoneyBinding.executePendingBindings()
    }
}