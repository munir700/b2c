package co.yap.household.onboarding.kycsuccess

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.BaseState
import javax.inject.Inject

class KycSuccessState @Inject constructor(): BaseState(), IKycSuccess.State {
    override var address: MutableLiveData<Address>? = MutableLiveData(Address())
}