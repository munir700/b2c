package co.yap.modules.dashboard.store.young.card

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.BaseState

class YoungCardEditDetailsState : BaseState(), IYoungCardEditDetails.State {
    override var designCode: MutableLiveData<String>? = MutableLiveData()
    override var cardDesigns: MutableLiveData<MutableList<HouseHoldCardsDesign>>? = MutableLiveData()
    override var cardName: MutableLiveData<String> = MutableLiveData()
    override var childName: MutableLiveData<String> = MutableLiveData("Lina")
    override var address: MutableLiveData<Address>? = MutableLiveData()
}
