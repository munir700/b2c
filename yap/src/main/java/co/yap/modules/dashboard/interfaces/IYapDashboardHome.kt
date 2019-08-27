package co.yap.modules.dashboard.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapDashboardHome {

    interface State : IBase.State {

    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent

    }

    interface View : IBase.View<ViewModel>
}