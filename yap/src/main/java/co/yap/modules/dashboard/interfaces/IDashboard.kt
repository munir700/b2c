package co.yap.modules.dashboard.interfaces

import co.yap.yapcore.IBase

interface IDashboard {

    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {

    }
}

