package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemVirtualCardBinding
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel

class VirtualCardViewHolder(private val itemYapVirtualCardBinding: ItemVirtualCardBinding) :
    RecyclerView.ViewHolder(itemYapVirtualCardBinding.root) {

    fun onBind(
        position: Int,
        virtualCard: VirtualCardModel?
    ) {
        itemYapVirtualCardBinding.viewModel = VirtualCardItemViewModel()
        itemYapVirtualCardBinding.executePendingBindings()
    }
}
