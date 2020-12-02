package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemVirtualCardBinding
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import co.yap.yapcore.helpers.extentions.dimen

class VirtualCardViewHolder(private val itemYapVirtualCardBinding: ItemVirtualCardBinding) :
    RecyclerView.ViewHolder(itemYapVirtualCardBinding.root) {

    init {
        val binding = itemYapVirtualCardBinding
        val params = binding.ivCard.layoutParams
        params.width = binding.ivCard.context.dimen(R.dimen._204sdp)
        params.height = binding.ivCard.context.dimen(R.dimen._225sdp)
        binding.ivCard.layoutParams = params
    }

    fun onBind(
        position: Int,
        virtualCard: VirtualCardModel?
    ) {
        itemYapVirtualCardBinding.viewModel = VirtualCardItemViewModel()
        itemYapVirtualCardBinding.executePendingBindings()
    }
}
