package co.yap.yapcore.dagger.base.navigation.host

import co.yap.yapcore.IBase

interface INavHostPresenter {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {

    }

    interface State : IBase.State {
    }
}