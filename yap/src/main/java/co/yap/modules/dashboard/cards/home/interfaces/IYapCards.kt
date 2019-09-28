package co.yap.modules.dashboard.cards.home.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapCards {

    interface State : IBase.State {
        var noOfCard: String
        var enableAddCard: ObservableBoolean
        var listUpdated: MutableLiveData<Boolean>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun getCards()
        fun handlePressOnView(id: Int)
        fun updateCardCount(id: Int)
    }

    interface View : IBase.View<ViewModel>
}