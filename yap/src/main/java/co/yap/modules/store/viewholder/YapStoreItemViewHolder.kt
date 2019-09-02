package co.yap.modules.store.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapStoreBinding
import co.yap.modules.store.models.YapStoreData
import co.yap.modules.store.viewmodels.YapStoreItemViewModel


class YapStoreItemViewHolder(private val itemYapStoreBinding: ItemYapStoreBinding) :
    RecyclerView.ViewHolder(itemYapStoreBinding.root) {

    fun onBind(store: YapStoreData) {
        itemYapStoreBinding.viewModel = YapStoreItemViewModel(store)
        itemYapStoreBinding.executePendingBindings()
    }
}