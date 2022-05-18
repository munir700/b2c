package co.yap.modules.dashboard.store.young.cardsuccess

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class YoungCardSuccessState @Inject constructor() : BaseState(), IYoungCardSuccess.State {
    override var childName: MutableLiveData<String> = MutableLiveData("Lina")
}