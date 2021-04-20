package co.yap.modules.dashboard.transaction.category

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemTapixCategoryBinding
import co.yap.networking.transactions.responsedtos.transaction.TapixCategory

class TransactionCategoryViewHolder(val binding: ItemTapixCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        position: Int,
        item: TapixCategory
    ) {
        binding.viewModel =
            TransactionCategoryItemViewModel(
                item,
                position
            )
        binding.executePendingBindings()
    }
}