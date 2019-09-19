package co.yap.modules.dashboard.cards.home.adaptor

import android.content.Context
import androidx.databinding.ViewDataBinding
import co.yap.R
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.viewholder.YapCardItemViewHolder
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils

class YapCardsAdaptor(context: Context, private val list: MutableList<Card>) :
    BaseBindingRecyclerAdapter<Card, YapCardItemViewHolder>(list) {

    private var dimensions: IntArray = Utils.getCardDimensions(context, 50, 45)

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_yap_card

    override fun onCreateViewHolder(binding: ViewDataBinding): YapCardItemViewHolder {
        return YapCardItemViewHolder(binding as ItemYapCardBinding)
    }

    override fun onBindViewHolder(holder: YapCardItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position], dimensions)
    }

    fun setItem(lists: List<Card>) {
        list.addAll(lists)
        notifyDataSetChanged()
    }
}
