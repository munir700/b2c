package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import co.yap.yapcore.BaseState

class AddVirtualCardState : BaseState(), IAddVirtualCard.State {
    override var designCode: MutableLiveData<String>? = MutableLiveData()
    override var cardDesigns: MutableLiveData<MutableList<VirtualCardModel>>? = MutableLiveData()
    override var cardName: ObservableField<String> = ObservableField()
    override var childName: MutableLiveData<String> = MutableLiveData("Lina")

    @get:Bindable
    override var enabelCoreButton: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.enabelCoreButton)
        }
}