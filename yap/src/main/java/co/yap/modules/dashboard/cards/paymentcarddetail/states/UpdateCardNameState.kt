package co.yap.modules.dashboard.cards.paymentcarddetail.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IUpdateCardName
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseState

class UpdateCardNameState : BaseState(), IUpdateCardName.State {

    @get:Bindable
    override var cardName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardName)
        }

    override var card: ObservableField<Card> = ObservableField()
}