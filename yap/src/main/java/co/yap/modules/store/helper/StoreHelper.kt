package co.yap.modules.store.helper

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.store.adaptor.YapStoreAdaptor
import co.yap.networking.store.responsedtos.Store

object StoreHelper {

    @JvmStatic
    @BindingAdapter("data")
    fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<Store>) {
        if (recyclerView.adapter is YapStoreAdaptor) {
            (recyclerView.adapter as YapStoreAdaptor).setList(items)
        }
    }
}