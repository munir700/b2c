package co.yap.modules.dashboard.cards.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.modols.PaymentCard
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardItemViewModel


class YapCardItemViewHolder(private val itemYapCardBinding: ItemYapCardBinding) :
    RecyclerView.ViewHolder(itemYapCardBinding.root) {

    fun onBind(paymentCard: PaymentCard?) {
        itemYapCardBinding.viewModel = YapCardItemViewModel(paymentCard)
        itemYapCardBinding.executePendingBindings()
    }
}