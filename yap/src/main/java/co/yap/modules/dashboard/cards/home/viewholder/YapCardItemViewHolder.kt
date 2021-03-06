package co.yap.modules.dashboard.cards.home.viewholder

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemYapCardBinding
import co.yap.modules.dashboard.cards.home.interfaces.SwipeUpClick
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardItemViewModel
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.OnSwipeTouchListener
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
        onItemClickListener: OnItemClickListener?,
        swipeUpClick: SwipeUpClick
    ) {
        itemYapCardBinding.viewModel =
            YapCardItemViewModel(context, paymentCard, position, onItemClickListener)
        val params = itemYapCardBinding.imgCard.layoutParams as ConstraintLayout.LayoutParams
        params.width = dimensions[0]
        params.height = dimensions[1]
        itemYapCardBinding.imgCard.layoutParams = params

        if (Constants.CARD_TYPE_DEBIT == paymentCard?.cardType) {
            if (SessionManager.isFounder.value == true) {
                itemYapCardBinding.imgCard.setImageResource(R.drawable.founder_front)
            } else {
                itemYapCardBinding.imgCard.setImageResource(R.drawable.card_spare)
            }
        } else {
            itemYapCardBinding.imgCard.loadCardImage(paymentCard?.frontImage)
        }

        itemYapCardBinding.lySeeDetail.lySeeDetailMain.setOnTouchListener(object :
            OnSwipeTouchListener(context) {
            override fun onSwipeUp() {
                super.onSwipeUp()
                swipeUpClick.onSwipeUp(position)
            }
        })
        // itemYapCardBinding.includeWalletButton.btnSamsungPay.setBackgroundResource(if (paymentCard?.isAddedSamsungPay == true) R.drawable.bg_gray_rounded_corner_black else R.drawable.bg_rounded_corner_black)

        itemYapCardBinding.executePendingBindings()
    }
}