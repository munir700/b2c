package co.yap.modules.dashboard.cards.home.viewholder

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardItemViewModel
import co.yap.networking.cards.responsedtos.Card


class YapCardItemViewHolder(private val itemYapCardBinding: ItemYapCardBinding) :
    RecyclerView.ViewHolder(itemYapCardBinding.root) {

    fun onBind(paymentCard: Card?, dimensions: IntArray) {

        val params = itemYapCardBinding.imgCard.layoutParams as ConstraintLayout.LayoutParams
        params.width = dimensions[0]
        params.height = dimensions[1]
        itemYapCardBinding.imgCard.layoutParams = params

        itemYapCardBinding.viewModel = YapCardItemViewModel(paymentCard)
        itemYapCardBinding.executePendingBindings()
    }
}