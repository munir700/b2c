package co.yap.modules.dashboard.addreceipt

import co.yap.yapcore.IBase

class IAddTransactionReceipt {
    interface View : IBase.View<ViewModel> {
        fun onCaptureProcessCompleted(filename: String?)
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
    }
}
