package co.yap.modules.dashboard.addionalinfo.interfaces

import co.yap.yapcore.IBase

interface IAdditionalInfo {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val stepList: ArrayList<String>
    }

    interface State : IBase.State
}