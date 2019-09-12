package co.yap.modules.dashboard.cards.home.adaptor

import androidx.databinding.ViewDataBinding
import co.yap.R
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.modols.PaymentCard
import co.yap.modules.dashboard.cards.home.viewholder.YapCardItemViewHolder
import co.yap.yapcore.BaseBindingRecyclerAdapter

class YapCardsAdaptor(private val list: MutableList<PaymentCard>) :
    BaseBindingRecyclerAdapter<PaymentCard, YapCardItemViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_yap_card

    override fun onCreateViewHolder(binding: ViewDataBinding): YapCardItemViewHolder {
        return YapCardItemViewHolder(binding as ItemYapCardBinding)
    }

    override fun onBindViewHolder(holder: YapCardItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position])
    }

    fun setItem(lists: List<PaymentCard>) {
        list.addAll(lists)
        notifyDataSetChanged()
    }
}
