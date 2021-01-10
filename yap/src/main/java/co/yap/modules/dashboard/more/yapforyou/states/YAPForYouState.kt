package co.yap.modules.dashboard.more.yapforyou.states

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.yapcore.BaseState

class YAPForYouState : BaseState(), IYAPForYou.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
    override var currentAchievement: ObservableField<Y4YAchievementData> = ObservableField()
    override var isNoCompletedAchievements: ObservableBoolean = ObservableBoolean()

}