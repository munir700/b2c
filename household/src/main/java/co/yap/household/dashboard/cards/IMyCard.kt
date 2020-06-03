package co.yap.household.dashboard.cards

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IMyCard {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_FREEZE_UNFREEZE_CARD: Int get() = 1
        val EVENT_CARD_DETAILS: Int get() = 2
        val clickEvent: SingleClickEvent
        fun freezeUnfreezeCard()
        fun getCardDetails()
        fun handlePressOnButtonClick(id: Int)
        fun getPrimaryCard(success: () -> Unit)
    }

    interface State : IBase.State {
        var card: MutableLiveData<Card>?
        var cardStatus: MutableLiveData<String?>
        var cardDetail: MutableLiveData<CardDetail?>
    }
}
