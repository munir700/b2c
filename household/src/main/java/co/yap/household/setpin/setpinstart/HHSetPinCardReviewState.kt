package co.yap.household.setpin.setpinstart

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseState

class HHSetPinCardReviewState : BaseState(), IHHSetPinCardReview.State {
    override var card: MutableLiveData<Card>? = MutableLiveData()
}