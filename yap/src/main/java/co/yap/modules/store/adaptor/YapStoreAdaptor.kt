package co.yap.modules.store.adaptor

import androidx.databinding.ViewDataBinding
import co.yap.R
import co.yap.databinding.ItemYapStoreBinding
import co.yap.modules.store.viewholder.YapStoreItemViewHolder
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseBindingRecyclerAdapter

class YapStoreAdaptor(private val list: MutableList<Store>) :
    BaseBindingRecyclerAdapter<Store, YapStoreItemViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_yap_store

    override fun onCreateViewHolder(binding: ViewDataBinding): YapStoreItemViewHolder {
        return YapStoreItemViewHolder(binding as ItemYapStoreBinding)
    }

    override fun onBindViewHolder(holder: YapStoreItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position])
    }

}
