package co.yap.modules.dashboard.store.young.cardsuccess

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungCardSuccess {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface State : IBase.State
}