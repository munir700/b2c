package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardcolour

import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemVirtualCardBinding
import co.yap.networking.cards.responsedtos.VirtualCardDesigns
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.loadCardImage

class VirtualCardViewHolder(private val itemYapVirtualCardBinding: ItemVirtualCardBinding) :
    RecyclerView.ViewHolder(itemYapVirtualCardBinding.root) {

    init {
        val binding = itemYapVirtualCardBinding
        val params = binding.ivCard.layoutParams
        params.width = binding.ivCard.context.dimen(R.dimen._250sdp)
        params.height = binding.ivCard.context.dimen(R.dimen._300sdp)
        binding.ivCard.layoutParams = params
    }

    fun onBind(
        position: Int,
        virtualCard: VirtualCardDesigns,
        cardName: ObservableField<String>
    ) {
        itemYapVirtualCardBinding.viewModel =
            VirtualCardItemViewModel(cardName, virtualCard, position)

        itemYapVirtualCardBinding.ivCard.loadCardImage(virtualCard.frontSideDesignImage)
        itemYapVirtualCardBinding.executePendingBindings()
    }
}
