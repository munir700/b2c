package co.yap.billpayments.paybill

import android.app.Application
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager

class PayBillViewModel(application: Application) :
    PayBillBaseViewModel<IPayBill.State>(application),
    IPayBill.ViewModel {

    override val state: IPayBill.State = PayBillState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun onCreate() {
        super.onCreate()
        state.availableBalanceString.set(context.resources.getText(
            getString(Strings.screen_cash_transfer_display_text_available_balance),
            context.color(
                R.color.colorPrimaryDark,
                SessionManager.cardBalance.value?.availableBalance?.toFormattedCurrency(showCurrency = true)
                    ?: ""
            )
        ))
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_pay_bill_text_title))
        toggleToolBarVisibility(true)
        toolgleRightIconVisibility(true)
        toogleLeftIconVisibility(false)
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }
}