package co.yap.modules.others

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.adaptor.CardStatementsAdaptor
import co.yap.networking.transactions.responsedtos.CardStatement

object BindingHelper {


    @BindingAdapter("adaptorList")
    @JvmStatic
    fun setAdaptor(recycleview: RecyclerView, list: ObservableField<List<CardStatement>>) {
        if (!list.get().isNullOrEmpty())
            if (recycleview.adapter is CardStatementsAdaptor)
                (recycleview.adapter as CardStatementsAdaptor).setList(list.get()!!)
    }
}