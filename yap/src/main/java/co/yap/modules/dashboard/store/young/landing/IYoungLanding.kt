package co.yap.modules.dashboard.store.young.landing

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungLanding {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface View : IBase.View<ViewModel>
}
