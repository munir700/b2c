package co.yap.household.onboarding.kycsuccess

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.BaseState

class KycSuccessState: BaseState(), IKycSuccess.State {
    override var address: MutableLiveData<Address>? = MutableLiveData(Address())
}