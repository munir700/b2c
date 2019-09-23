package co.yap.modules.dashboard.helpers

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.viewpager2.widget.ViewPager2
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.networking.cards.responsedtos.Card

object BindingHelper {


    @BindingAdapter("adaptorList")
    @JvmStatic
    fun setAdaptor(viewPager2: ViewPager2, list: ObservableField<ArrayList<Card>>) {
        if (viewPager2 != null && list != null && !list.get().isNullOrEmpty())
            if (viewPager2.adapter is YapCardsAdaptor)
                (viewPager2.adapter as YapCardsAdaptor).setList(list.get()!!)
    }
}