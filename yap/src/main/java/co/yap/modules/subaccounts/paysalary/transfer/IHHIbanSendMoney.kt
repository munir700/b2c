package co.yap.modules.subaccounts.paysalary.transfer

import co.yap.yapcore.IBase

interface IHHIbanSendMoney {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {

    }

    interface State : IBase.State {
    }
}
