package co.yap.modules.dashboard.cards.home.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapCards {

    interface State : IBase.State {
        //var cards: MutableLiveData<ArrayList<Card>>
        var cardList: ObservableField<ArrayList<Card>>
        var noOfCard: String
        var enableAddCard: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun getCards()
        fun handlePressOnView(id: Int)
        fun updateCardCount(id: Int)
    }

    interface View : IBase.View<ViewModel>
}