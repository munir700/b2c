package co.yap.app.modules.login.interfaces

import co.yap.yapcore.IBase

interface ISystemPermission {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var screenType: String
    }

    interface State : IBase.State {
        var icon: Int
        var title: String
        var termsAndConditionsVisibility: Boolean
        var buttonTitle: String
    }
}