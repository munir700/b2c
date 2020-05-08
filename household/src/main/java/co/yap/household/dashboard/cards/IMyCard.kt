package co.yap.household.dashboard.cards

import co.yap.yapcore.IBase

class IMyCard {
    interface View: IBase.View<ViewModel> {

    }

    interface ViewModel: IBase.ViewModel<State> {

    }

    interface State: IBase.State {

    }
}