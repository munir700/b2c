package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYapForYouMain
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.modules.dashboard.more.yapforyou.states.YapForYouMainState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapForYouMainViewModel(application: Application) :
    BaseViewModel<IYapForYouMain.State>(application),
    IYapForYouMain.ViewModel {

    override val state: YapForYouMainState = YapForYouMainState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var selectedAchievement: Y4YAchievementData? = null
    override var selectedAchievementTask: YAPForYouGoal? = null
    override var selectedPosition: Int = 0
    override var achievementsList: MutableList<Y4YAchievementData> = mutableListOf()

    override fun handlePressButton(id: Int) {
        clickEvent.setValue(id)
    }
}
