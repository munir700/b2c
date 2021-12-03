package co.yap.app.modules.startup.states

import androidx.lifecycle.MutableLiveData
import co.yap.app.modules.startup.interfaces.ISplash
import co.yap.networking.messages.responsedtos.DownTime
import co.yap.yapcore.BaseState

class SplashState : BaseState(), ISplash.State{
    override val downTime: MutableLiveData<DownTime> = MutableLiveData()
}