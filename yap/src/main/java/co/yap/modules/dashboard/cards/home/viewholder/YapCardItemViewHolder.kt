package co.yap.modules.dashboard.cards.home.viewholder

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardItemViewModel
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.translation.Strings.screen_spare_card_landing_display_text_virtual_card
import co.yap.translation.Translator.getString
import co.yap.yapcore.helpers.extentions.loadCardImage
import co.yap.yapcore.interfaces.OnItemClickListener


class YapCardItemViewHolder(private val itemYapCardBinding: ItemYapCardBinding) :
    RecyclerView.ViewHolder(itemYapCardBinding.root) {

    fun onBind(
        position: Int,
        paymentCard: Card?,
        dimensions: IntArray,
        onItemClickListener: OnItemClickListener?
    ) {
        val params = itemYapCardBinding.imgCard.layoutParams as ConstraintLayout.LayoutParams
        params.width = dimensions[0]
        params.height = dimensions[1]
        itemYapCardBinding.imgCard.layoutParams = params
        itemYapCardBinding.imgCard.loadCardImage(paymentCard?.frontImage)
        val cardName: String

        if (Constants.CARD_TYPE_DEBIT == paymentCard?.cardType) {
            cardName = Constants.TEXT_PRIMARY_CARD
        } else {
            if (null != paymentCard?.nameUpdated) {
                if (paymentCard.nameUpdated!!) {
                    cardName = paymentCard.cardName ?: ""
                } else {
                    if (paymentCard.physical) {
                        cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
                    } else {
                        cardName = paymentCard.cardName ?: ""
                        /* cardName = getString(
                             itemYapCardBinding.tvCardName.context,
                             screen_spare_card_landing_display_text_virtual_card
                         )*/
                    }
                }
            } else {
                if (paymentCard?.physical!!) {
                    cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
                } else {
                    cardName = paymentCard.cardName ?: ""
                }

            }

        }
        itemYapCardBinding.tvCardName.text = cardName
        itemYapCardBinding.viewModel =
            YapCardItemViewModel(paymentCard, position, onItemClickListener)
        itemYapCardBinding.executePendingBindings()
    }
}