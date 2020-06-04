package co.yap.household.dashboard.cards

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.yapcore.BaseState

class MyCardState : BaseState(), IMyCard.State {
    override var cardStatus: MutableLiveData<String?> = MutableLiveData("Freeze card")
    override var cardDetail: MutableLiveData<CardDetail>? = MutableLiveData()
    override var card: MutableLiveData<Card>? = MutableLiveData()
}
