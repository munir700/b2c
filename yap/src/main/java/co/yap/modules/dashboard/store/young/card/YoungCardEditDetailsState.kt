package co.yap.modules.dashboard.store.young.card

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class YoungCardEditDetailsState : BaseState(), IYoungCardEditDetails.State {
    override var designCode: MutableLiveData<String>? = MutableLiveData()
    override var cardDesigns: MutableLiveData<MutableList<YoungCardsDesign>>? = MutableLiveData()
    override var cardName: MutableLiveData<String> = MutableLiveData()
    override var childName: MutableLiveData<String> = MutableLiveData("Lina")

}