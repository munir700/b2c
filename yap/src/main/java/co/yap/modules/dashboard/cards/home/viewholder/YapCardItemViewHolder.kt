package co.yap.modules.dashboard.cards.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardItemViewModel
import co.yap.networking.cards.responsedtos.Card


class YapCardItemViewHolder(private val itemYapCardBinding: ItemYapCardBinding) :
    RecyclerView.ViewHolder(itemYapCardBinding.root) {

    fun onBind(paymentCard: Card?) {
        itemYapCardBinding.viewModel = YapCardItemViewModel(paymentCard)
        itemYapCardBinding.executePendingBindings()
    }
}