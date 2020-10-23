package co.yap.modules.dashboard.cards.home.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapCards {

    interface State : IBase.State {
        var noOfCard: String
        var enableAddCard: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val cards: MutableLiveData<ArrayList<Card>>
        fun getCards()
        fun handlePressOnView(id: Int)
        fun updateCardCount(id: Int)
        fun getPrimaryCard(cards: ArrayList<Card>?):Card?
        fun getDebitCard()
    }

    interface View : IBase.View<ViewModel>
}