package co.yap.household.dashboard.more

import co.yap.yapcore.IBase

interface IHouseHoldMore {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun logout(success: () -> Unit)
    }

    interface State : IBase.State
}