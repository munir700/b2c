package co.yap.household.setpin.setpinstart

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHSetPinCardReviewState @Inject constructor(): BaseState(), IHHSetPinCardReview.State {
    override var card: MutableLiveData<Card>? = MutableLiveData()
}