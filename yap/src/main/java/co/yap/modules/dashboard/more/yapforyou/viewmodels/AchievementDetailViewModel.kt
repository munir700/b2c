package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.yapforyou.adapters.AchievementTaskAdaptor
import co.yap.modules.dashboard.more.yapforyou.interfaces.IAchievementDetail
import co.yap.modules.dashboard.more.yapforyou.states.AchievementDetailState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class AchievementDetailViewModel(application: Application) :
    BaseViewModel<IAchievementDetail.State>(application),
    IAchievementDetail.ViewModel {

    override val state: AchievementDetailState = AchievementDetailState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val adapter: AchievementTaskAdaptor = AchievementTaskAdaptor(mutableListOf())
    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
//        adapter.setList(par)
    }
}