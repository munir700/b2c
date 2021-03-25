package co.yap.modules.dashboard.cards.paymentcarddetail.activities.carddetaildialog

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class CardDetailsDialogItemViewModel(
    var position: Int?,
    var cardDetailsModel: CardDetailsModel,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, cardDetailsModel, position ?: 0)
    }
}