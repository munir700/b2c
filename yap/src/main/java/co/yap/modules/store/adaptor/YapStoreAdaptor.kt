package co.yap.modules.store.adaptor

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemYapStoreBinding
import co.yap.modules.store.viewholder.YapStoreItemViewHolder
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BasePagingBindingRecyclerAdapter
import co.yap.yapcore.databinding.ItemListFooterBinding

class YapStoreAdaptor(retry: () -> Unit) :
    BasePagingBindingRecyclerAdapter<Store>(retry, diffCallback) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_yap_store

    override fun getLayoutIdForFooterType(viewType: Int): Int = R.layout.item_list_footer

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == contentView)
            (holder as YapStoreItemViewHolder).onBind(getItem(position))
        else (holder as ListFooterViewHolder).onBind(getState())
    }

    override fun onCreateContentViewHolder(binding: ViewDataBinding): YapStoreItemViewHolder {
        return YapStoreItemViewHolder(binding as ItemYapStoreBinding)
    }

    override fun onCreateFooterViewHolder(binding: ViewDataBinding): ListFooterViewHolder {
        return ListFooterViewHolder(binding as ItemListFooterBinding)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Store>() {
            override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean =
                oldItem == newItem
        }
    }
}
