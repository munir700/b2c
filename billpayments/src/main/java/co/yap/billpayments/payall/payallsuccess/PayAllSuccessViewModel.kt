package co.yap.billpayments.payall.payallsuccess

import android.app.Application
import co.yap.billpayments.paybill.base.PayBillMainBaseViewModel
import co.yap.billpayments.paybill.paybillsuccess.PayBillSuccessState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

class PayAllSuccessViewModel(application: Application) :
    PayBillMainBaseViewModel<IPayAllSuccess.State>(application),
    IPayAllSuccess.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPayAllSuccess.State = PayAllSuccessState()

    override fun onResume() {
        super.onResume()
        toggleRightIconVisibility(false)
        setToolBarTitle(getString(Strings.screen_pay_bill_success_toolbar_title))
        state.paidAmount.set(
            parentViewModel?.state?.paidAmount?.get()
                .toFormattedCurrency(showCurrency = true, withComma = true)
        )
        state.inputFieldString.set(
            parentViewModel?.billModel?.value?.inputsData?.joinToString(
                separator = " | "
            ) { billerInputData -> billerInputData.value.toString() })
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }
}