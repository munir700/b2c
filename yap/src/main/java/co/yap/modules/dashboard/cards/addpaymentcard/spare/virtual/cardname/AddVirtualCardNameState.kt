package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardname

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.yapcore.BaseState

class AddVirtualCardNameState : BaseState(),
    IAddVirtualCardName.State {
    override var cardName: ObservableField<String> = ObservableField()

    @get:Bindable
    override var enabelCoreButton: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.enabelCoreButton)
        }
}