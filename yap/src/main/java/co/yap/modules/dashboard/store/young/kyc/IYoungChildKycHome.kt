package co.yap.modules.dashboard.store.young.kyc

import co.yap.yapcore.IBase

interface IYoungChildKycHome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
    }
}