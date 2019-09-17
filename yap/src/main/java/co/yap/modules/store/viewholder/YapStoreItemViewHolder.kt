package co.yap.modules.store.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapStoreBinding
import co.yap.modules.store.viewmodels.YapStoreItemViewModel
import co.yap.networking.store.responsedtos.Store


class YapStoreItemViewHolder(private val itemYapStoreBinding: ItemYapStoreBinding) :
    RecyclerView.ViewHolder(itemYapStoreBinding.root) {

    fun onBind(store: Store?) {
        itemYapStoreBinding.viewModel = YapStoreItemViewModel(store)
        itemYapStoreBinding.executePendingBindings()
    }
}