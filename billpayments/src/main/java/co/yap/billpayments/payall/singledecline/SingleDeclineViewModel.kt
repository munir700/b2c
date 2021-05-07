package co.yap.billpayments.payall.singledecline

import android.app.Application
import co.yap.billpayments.paybill.base.PayBillMainBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

class SingleDeclineViewModel(application: Application) :
    PayBillMainBaseViewModel<ISingleDecline.State>(application),
    ISingleDecline.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ISingleDecline.State = SingleDeclineState()

    override fun onResume() {
        super.onResume()
        toggleRightIconVisibility(false)
        setToolBarTitle(getString(Strings.screen_single_decline_toolbar_text_decline))
        state.paidAmount.set(
            parentViewModel?.state?.paidAmount?.get()
                .toFormattedCurrency(showCurrency = true, withComma = true)
        )
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }
}