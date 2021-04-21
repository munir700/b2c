package co.yap.billpayments.paybill.main

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class PayBillMainViewModel(application: Application) :
    BaseViewModel<IPayBillMain.State>(application),
    IPayBillMain.ViewModel {

    override val state: IPayBillMain.State = PayBillMainState()
    override var onToolbarClickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressView(id: Int) {
        onToolbarClickEvent.setValue(id)
    }
}