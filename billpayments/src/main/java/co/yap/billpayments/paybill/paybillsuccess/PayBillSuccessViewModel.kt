package co.yap.billpayments.paybill.paybillsuccess

import android.app.Application
import co.yap.billpayments.paybill.base.PayBillMainBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager

class PayBillSuccessViewModel(application: Application) :
    PayBillMainBaseViewModel<IPayBillSuccess.State>(application),
    IPayBillSuccess.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPayBillSuccess.State = PayBillSuccessState()

    override fun onResume() {
        super.onResume()
        toggleRightIconVisibility(false)
        setToolBarTitle(getString(Strings.screen_pay_bill_success_toolbar_title))
        state.paidAmount.set(
            parentViewModel?.state?.paidAmount?.get()
                .toFormattedCurrency(showCurrency = true, withComma = true)
        )
        state.customerAccountNumber.set(SessionManager.user?.accountNo ?: "")
        state.customerFullName.set(SessionManager.user?.currentCustomer?.getFullName() ?: "")
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }
}
