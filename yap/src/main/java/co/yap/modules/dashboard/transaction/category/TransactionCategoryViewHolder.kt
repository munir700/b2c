package co.yap.modules.dashboard.transaction.category

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemTapixCategoryBinding
import co.yap.networking.transactions.responsedtos.transaction.TapixCategory
import co.yap.yapcore.interfaces.OnItemClickListener

class TransactionCategoryViewHolder(val binding: ItemTapixCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        position: Int,
        item: TapixCategory,
        onItemClickListener: OnItemClickListener?
    ) {
        binding.viewModel =
            TransactionCategoryItemViewModel(
                item,
                position, onItemClickListener
            )
        binding.executePendingBindings()
    }
}