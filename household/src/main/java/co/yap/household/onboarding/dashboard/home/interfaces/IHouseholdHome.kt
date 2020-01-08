package co.yap.household.onboarding.dashboard.home.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseholdHome {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var viewState: MutableLiveData<Int>
        fun handlePressOnView(id: Int)
        fun requestTransactions()
    }

    interface State : IBase.State
}