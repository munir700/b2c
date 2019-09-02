package co.yap.modules.store.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapStoreDetailBinding
import co.yap.modules.store.models.YapStoreData
import co.yap.modules.store.viewmodels.YapStoreDetailItemViewModel


class YapStoreDetailItemViewHolder(private val itemYapStoreDetailBinding: ItemYapStoreDetailBinding) :
    RecyclerView.ViewHolder(itemYapStoreDetailBinding.root) {

    fun onBind(store: YapStoreData) {
        itemYapStoreDetailBinding.viewModel = YapStoreDetailItemViewModel(store)
        itemYapStoreDetailBinding.executePendingBindings()
    }
}