package co.yap.modules.store.adaptor

import androidx.databinding.ViewDataBinding
import co.yap.R
import co.yap.databinding.ItemYapStoreDetailBinding
import co.yap.modules.store.models.YapStoreData
import co.yap.modules.store.viewholder.YapStoreDetailItemViewHolder
import co.yap.yapcore.BaseBindingRecyclerAdapter

class YapStoreDetailAdaptor(private val list: MutableList<YapStoreData>) :
    BaseBindingRecyclerAdapter<YapStoreData, YapStoreDetailItemViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_yap_store_detail

    override fun onCreateViewHolder(binding: ViewDataBinding): YapStoreDetailItemViewHolder {
        return YapStoreDetailItemViewHolder(binding as ItemYapStoreDetailBinding)
    }

    override fun onBindViewHolder(holder: YapStoreDetailItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position])
    }

}
