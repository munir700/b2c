package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topupactivity

import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.yapcore.IBase

interface ITopUp {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>{
        var leanCustomerAccounts: LeanCustomerAccounts
        var customerID: String
    }
    interface View : IBase.View<ViewModel>{
        fun setupNavHostFragmentWithData()
    }
}