package co.yap.billpayments.paybills

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class PayBillsViewModel(application: Application) :
    PayBillBaseViewModel<IPayBills.State>(application),
    IPayBills.ViewModel {

    override val state: IPayBills.State = PayBillsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_bill_payment_text_title))
        toggleToolBarVisibility(true)
        parentViewModel?.state?.leftIconVisibility?.set(true)
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }
}