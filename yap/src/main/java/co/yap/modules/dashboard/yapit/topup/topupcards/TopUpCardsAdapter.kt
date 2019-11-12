package co.yap.modules.dashboard.yapit.topup.topupcards

import android.content.Context
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTopupCardsBinding
import co.yap.databinding.ItemYapCardEmptyBinding
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardEmptyItemViewModel
import co.yap.modules.dashboard.home.models.Notification
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener


class TopUpCardsAdapter(
    context: Context,
    val list: MutableList<Notification>
) :
    BaseBindingRecyclerAdapter<Notification, RecyclerView.ViewHolder>(list) {

    private val empty = 1
    private val actual = 2
    private var dimensions: IntArray = Utils.getCardDimensions(context, 50, 45)

    override fun getLayoutIdForViewType(viewType: Int): Int =
        if (viewType == actual) R.layout.item_topup_cards else R.layout.item_yap_card_empty


    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return if (binding is ItemTopupCardsBinding) TopUpCardViewHolder(binding) else TopUpEmptyItemViewHolder(
            binding as ItemYapCardEmptyBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is TopUpCardViewHolder) {
            holder.onBind(position, list[position] as Card, dimensions, onItemClickListener)
        } else {
            if (holder is TopUpEmptyItemViewHolder)
                holder.onBind(position, list[position] as Card, dimensions, onItemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].title == "addCard") empty else actual
    }


    inner class TopUpCardViewHolder(binding: ItemTopupCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(
            position: Int,
            paymentCard: Card?,
            dimensions: IntArray,
            onItemClickListener: OnItemClickListener?
        ) {

//            val params = itemYapCardBinding.imgCard.layoutParams as ConstraintLayout.LayoutParams
//            params.width = dimensions[0]
//            params.height = dimensions[1]
//            itemYapCardBinding.imgCard.layoutParams = params
//
//            var cardName: String
//
//            if (Constants.CARD_TYPE_DEBIT == paymentCard?.cardType) {
//                cardName = Constants.TEXT_PRIMARY_CARD
//            } else {
//                if (null != paymentCard?.nameUpdated) {
//                    if (paymentCard.nameUpdated!!) {
//                        cardName = paymentCard.cardName
//                    } else {
//                        if (paymentCard.physical) {
//                            cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
//                        } else {
//                            cardName = Constants.TEXT_SPARE_CARD_VIRTUAL
//                        }
//                    }
//                } else {
//                    if (paymentCard?.physical!!) {
//                        cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
//                    } else {
//                        cardName = Constants.TEXT_SPARE_CARD_VIRTUAL
//                    }
//
//                }
//
//            }
//            itemYapCardBinding.tvCardName.text = cardName
//            itemYapCardBinding.viewModel =
//                YapCardItemViewModel(paymentCard, position, onItemClickListener)
//            itemYapCardBinding.executePendingBindings()
        }
    }

    class TopUpEmptyItemViewHolder(private val itemYapCardEmptyBinding: ItemYapCardEmptyBinding) :
        RecyclerView.ViewHolder(itemYapCardEmptyBinding.root) {

        fun onBind(
            position: Int,
            paymentCard: Card?,
            dimensions: IntArray,
            onItemClickListener: OnItemClickListener?
        ) {

            val params = itemYapCardEmptyBinding.imgCard.layoutParams as FrameLayout.LayoutParams
            params.width = dimensions[0]
            params.height = dimensions[1]
            itemYapCardEmptyBinding.imgCard.layoutParams = params

            itemYapCardEmptyBinding.viewModel =
                YapCardEmptyItemViewModel(paymentCard, position, onItemClickListener)
            itemYapCardEmptyBinding.executePendingBindings()
        }
    }
}