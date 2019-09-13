package co.yap.modules.dashboard.cards.home.adaptor

import androidx.databinding.ViewDataBinding
import co.yap.R
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.viewholder.YapCardItemViewHolder
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingRecyclerAdapter

class YapCardsAdaptor(private val list: MutableList<Card>) :
    BaseBindingRecyclerAdapter<Card, YapCardItemViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_yap_card

    override fun onCreateViewHolder(binding: ViewDataBinding): YapCardItemViewHolder {
        return YapCardItemViewHolder(binding as ItemYapCardBinding)
    }

    override fun onBindViewHolder(holder: YapCardItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position])
    }

    fun setItem(lists: List<Card>) {
        list.addAll(lists)
        notifyDataSetChanged()
    }
}
