package co.yap.household.setpin.setpinstart

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.IBase

interface IHHSetPinCardReview {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun getCard()
    }

    interface State : IBase.State {
        var card: MutableLiveData<Card>?
    }
}