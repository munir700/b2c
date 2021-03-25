package co.yap.modules.dashboard.cards.paymentcarddetail.activities.carddetaildialog

import android.view.View
import android.widget.Toast
import co.yap.yapcore.helpers.extentions.toast
import com.ezaka.customer.app.utils.copyTextToClipboard

class CardDetailsDialogItemViewModel(
    var position: Int?,
    var cardDetailsModel: CardDetailsModel
) {
    fun onViewClicked(view: View) {
        cardDetailsModel.cardNumber?.copyTextToClipboard(view.context)
        view.context.toast("Copied to clipboard", Toast.LENGTH_SHORT)
    }
}