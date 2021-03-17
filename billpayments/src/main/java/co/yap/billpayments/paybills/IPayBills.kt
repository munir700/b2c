package co.yap.billpayments.paybills

import co.yap.yapcore.IBase

interface IPayBills {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel>
}