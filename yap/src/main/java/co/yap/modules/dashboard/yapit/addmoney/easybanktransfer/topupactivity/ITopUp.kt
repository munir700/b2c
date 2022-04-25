package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topupactivity

import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.IBase

interface ITopUp {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>{
        var leanCustomerAccounts: LeanCustomerAccounts
        var customerID: String
        var bankListMainModel: BankListMainModel
    }
    interface View : IBase.View<ViewModel>
}