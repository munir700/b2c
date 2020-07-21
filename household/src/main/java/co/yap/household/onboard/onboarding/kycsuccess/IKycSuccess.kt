package co.yap.household.onboard.onboarding.kycsuccess

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.IBase

interface IKycSuccess {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State{
        var address: MutableLiveData<Address>?
    }
}