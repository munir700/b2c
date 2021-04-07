package co.yap.modules.dashboard.cards.home.viewholder

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.BR
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardItemViewModel
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.helpers.extentions.loadCardImage
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager


class YapCardItemViewHolder(
    private val context: Context,
    private val itemYapCardBinding: ItemYapCardBinding
) :
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
        val cardName: String

        if (Constants.CARD_TYPE_DEBIT == paymentCard?.cardType) {
            cardName = Constants.TEXT_PRIMARY_CARD
            if (SessionManager.isFounder.value == true) {
                itemYapCardBinding.imgCard.setImageResource(R.drawable.founder_front)
            } else {
                itemYapCardBinding.imgCard.setImageResource(R.drawable.card_spare)
            }
        } else {
            itemYapCardBinding.imgCard.loadCardImage(paymentCard?.frontImage)
            if (null != paymentCard?.nameUpdated) {
                if (paymentCard.nameUpdated!!) {
                    cardName = paymentCard.cardName ?: ""
                } else {
                    if (paymentCard.physical) {
                        cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
                    } else {
                        cardName = paymentCard.cardName ?: ""
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
            YapCardItemViewModel(context,paymentCard, position, onItemClickListener)
        itemYapCardBinding.executePendingBindings()
    }
}