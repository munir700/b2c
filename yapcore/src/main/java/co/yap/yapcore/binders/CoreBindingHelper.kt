package co.yap.yapcore.binders

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.transactions.TransactionRecyclerView

object CoreBindingHelper {

//    @JvmStatic
//    @BindingAdapter(value = ["adaptorList", "isLoadMore"], requireAll = true)
//    fun setAdaptor(
//        recyclerView: TransactionRecyclerView,
//        list: ObservableField<MutableList<HomeTransactionListData>>,
//        isLoadMore: Boolean
//    ) {
//        if (isLoadMore) {
//            if (recyclerView.getRecycleViewAdaptor()?.itemCount?:0 > 0)
//                recyclerView.getRecycleViewAdaptor()?.removeItemAt(recyclerView.getRecycleViewAdaptor()?.itemCount?:0 - 1)
//
//            recyclerView.addListToAdapter(list.get() ?: mutableListOf())
//        } else {
//            if (!list.get().isNullOrEmpty()) {
//                recyclerView.setListToAdapter(list.get() ?: mutableListOf())
//            }
//        }
//    }
}