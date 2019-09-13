package co.yap.modules.dashboard.cards.home.states

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseState

class YapCardsState : BaseState(), IYapCards.State {

    @get:Bindable
    override var noOfCard: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.noOfCard)
        }

    @get:Bindable
    override var cards: MutableLiveData<List<Card>> = MutableLiveData()
        set(value) {
            field = value
            notifyPropertyChanged(BR.cards)
        }

}