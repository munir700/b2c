package co.yap.modules.dashboard.store.young.benefits

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungBenefits {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface View : IBase.View<ViewModel>
}
