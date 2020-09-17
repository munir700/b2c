package co.yap.modules.dashboard.store.young.cardsuccess

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class YoungCardSuccessState : BaseState(), IYoungCardSuccess.State {
    override var childName: MutableLiveData<String> = MutableLiveData("Lina")
}