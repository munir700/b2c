package co.yap.modules.dashboard.store.yapstore

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapStore {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State> {
    }
    interface View : IBase.View<ViewModel>
}