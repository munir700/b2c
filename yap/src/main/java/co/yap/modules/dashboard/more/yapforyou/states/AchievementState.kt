package co.yap.modules.dashboard.more.yapforyou.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.yapforyou.interfaces.IAchievement
import co.yap.yapcore.BaseState

class AchievementState : BaseState(), IAchievement.State {
    override var achievementIcon: ObservableField<Int> = ObservableField(-1)
}
