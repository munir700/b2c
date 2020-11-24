package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import co.yap.yapcore.BaseState

class AddVirtualCardState: BaseState(), IAddVirtualCard.State {
    override var designCode: MutableLiveData<String>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var cardDesigns: MutableLiveData<MutableList<VirtualCardModel>>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var cardName: MutableLiveData<String>
        get() = TODO("Not yet implemented")
        set(value) {}
    override var childName: MutableLiveData<String>
        get() = TODO("Not yet implemented")
        set(value) {}
}