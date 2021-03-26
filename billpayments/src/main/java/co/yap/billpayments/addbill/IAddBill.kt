package co.yap.billpayments.addbill

import co.yap.yapcore.IBase

interface IAddBill {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
    }
}