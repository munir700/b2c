package co.yap.modules.store.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapStoreBinding
import co.yap.modules.store.models.YapStoreData
import co.yap.yapcore.helpers.loadImage

class YapStoreItemViewHolder(private val view: ItemYapStoreBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun onBind(store: YapStoreData) {
        view.tvStoreName.text = store.name
        view.tvStoreDesc.text = store.desc
        view.imgStoreCover.loadImage(store.image)
    }
}