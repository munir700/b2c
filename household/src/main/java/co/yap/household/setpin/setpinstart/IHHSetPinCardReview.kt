package co.yap.household.setpin.setpinstart

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSetPinCardReview {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handleClick(id: Int)
        var clickEvent: SingleClickEvent
        val card: MutableLiveData<Card>
        fun getCard()
    }

    interface State : IBase.State
}