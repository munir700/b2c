package co.yap.widgets.scanqrcode

import co.yap.yapcore.IBase

interface IScanQRCode {
    interface State : IBase.State {

    }

    interface ViewModel : IBase.ViewModel<State> {

    }

    interface View : IBase.View<ViewModel> {

    }
}