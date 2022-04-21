package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.paymentsuccessful

import android.app.Application
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel

class PaymentSuccessfulViewModel(application: Application):AddMoneyBaseViewModel<IPaymentSuccessful.State>(application),
    IPaymentSuccessful.ViewModel {
    override val state: IPaymentSuccessful.State = PaymentSuccessfulState()
}