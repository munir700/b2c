package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.networking.transactions.responsedtos.achievement.Achievement

interface IY4YComposer {
    fun compose(response: ArrayList<Achievement>):ArrayList<Y4YAchievementData>
}