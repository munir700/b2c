package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.paymentsuccessful

import co.yap.yapcore.IBase

interface IPaymentSuccessful {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {

    }

    interface State : IBase.State {

    }
}