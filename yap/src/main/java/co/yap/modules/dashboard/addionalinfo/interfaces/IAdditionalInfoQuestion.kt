package co.yap.modules.dashboard.addionalinfo.interfaces

import co.yap.yapcore.IBase

interface IAdditionalInfoQuestion {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>{
        fun moveToNext()
    }

    interface State : IBase.State
}