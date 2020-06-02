package co.yap.household.dashboard.cards

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class MyCardState : BaseState(), IMyCard.State {
    override var cardStatus: MutableLiveData<String?> = MutableLiveData("Freeze card")
}
