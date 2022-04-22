package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.paymentsuccessful

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.helpers.spannables.size

class PaymentSuccessfulViewModel(application: Application):AddMoneyBaseViewModel<IPaymentSuccessful.State>(application),
    IPaymentSuccessful.ViewModel {

    override val state: IPaymentSuccessful.State = PaymentSuccessfulState()

    override fun setNewBalanceData() {
        state.newBalanceText.value =
            context.resources.getText(
                getString(Strings.screen_lean_topup_payment_successful_new_balance_text),
                context.color(
                    R.color.colorPrimaryDark,
                     size(1.5f,"1000".toFormattedCurrency())
                )
            )
    }
}