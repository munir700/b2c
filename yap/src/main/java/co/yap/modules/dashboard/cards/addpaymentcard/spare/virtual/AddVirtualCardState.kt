package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import co.yap.yapcore.BaseState

class AddVirtualCardState : BaseState(), IAddVirtualCard.State {
    override var designCode: MutableLiveData<String>? = MutableLiveData()
    override var cardDesigns: MutableLiveData<MutableList<VirtualCardModel>>? = MutableLiveData()
    override var cardName: MutableLiveData<String> = MutableLiveData()
    override var childName: MutableLiveData<String> = MutableLiveData("Lina")
}