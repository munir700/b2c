package co.yap.modules.dashboard.helpers

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.adaptor.CardStatementsAdaptor
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.CardStatement

object BindingHelper {


    @BindingAdapter("adaptorList")
    @JvmStatic
    fun setAdaptor(viewPager2: ViewPager2, list: ObservableField<ArrayList<Card>>) {
        if (viewPager2 != null && list != null && !list.get().isNullOrEmpty())
            if (viewPager2.adapter is YapCardsAdaptor)
                (viewPager2.adapter as YapCardsAdaptor).setList(list.get()!!)
    }

    @BindingAdapter("adaptorList")
    @JvmStatic
    fun setAdaptor(recycleview: RecyclerView, list: ObservableField<List<CardStatement>>) {
        if (!list.get().isNullOrEmpty())
            if (recycleview.adapter is CardStatementsAdaptor)
                (recycleview.adapter as CardStatementsAdaptor).setList(list.get()!!)
    }
}