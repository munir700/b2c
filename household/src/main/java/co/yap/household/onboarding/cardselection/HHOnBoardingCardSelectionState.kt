package co.yap.household.onboarding.cardselection

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHOnBoardingCardSelectionState @Inject constructor():BaseState(), IHHOnBoardingCardSelection.State {
    override var address: MutableLiveData<Address>?= MutableLiveData()
    override var cardDesigns: MutableLiveData<MutableList<HouseHoldCardsDesign>>? = MutableLiveData()
    override var designCode: MutableLiveData<String>? = MutableLiveData()
}
