package co.yap.modules.dashboard.cards.home.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.yapcore.BaseState

class YapCardsState : BaseState(), IYapCards.State {

    override var enableAddCard: ObservableBoolean = ObservableBoolean(false)
    override var listUpdated: MutableLiveData<Boolean> = MutableLiveData()

    @get:Bindable
    override var noOfCard: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.noOfCard)
        }
}