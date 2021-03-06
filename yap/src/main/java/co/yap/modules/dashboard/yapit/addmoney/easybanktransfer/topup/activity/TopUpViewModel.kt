package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.activity

import android.app.Application
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.yapcore.BaseViewModel

class TopUpViewModel(application: Application) :
    BaseViewModel<ITopUp.State>(application), ITopUp.ViewModel {
    override val state: ITopUp.State = TopUpState()
    override var leanCustomerAccounts: LeanCustomerAccounts = LeanCustomerAccounts()
    override var customerID: String = ""
}