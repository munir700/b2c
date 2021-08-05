package co.yap.billpayments.payall.singledecline

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.billpayments.payall.base.PayAllBaseViewModel
import co.yap.billpayments.payall.payallsuccess.adapter.PaidBill
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.billpayments.utils.enums.BillPaymentStatus
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

class SingleDeclineViewModel(application: Application) :
    PayAllBaseViewModel<ISingleDecline.State>(application),
    ISingleDecline.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ISingleDecline.State = SingleDeclineState()
    override var declinedBill: ObservableField<PaidBill> = ObservableField()
    override fun onResume() {
        super.onResume()
        toggleRightIconVisibility(false)
        setToolBarTitle(getString(Strings.screen_single_decline_toolbar_text_decline))
        declinedBill.set(parentViewModel?.paidBills?.first { it.paymentStatus == BillPaymentStatus.FAILEDTITLE.title })
        state.paidAmount.set(
            declinedBill.get()?.billAmount.toString()
                .toFormattedCurrency(showCurrency = true, withComma = true)
        )
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }
}