package co.yap.modules.dashboard.widgets

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.IBase

interface IEditWidget {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State {
        var toolBarVisibility: ObservableBoolean?
        var toolBarRightIconVisibility: ObservableBoolean?
    }
}