package co.yap.modules.dashboard.more.yapforyou.models

import androidx.annotation.ColorInt
import java.util.*

data class Y4YAchievementData(
    var title: String,
    var completedPercentage: Int,
    @ColorInt var tintColor: Int,
    var isLocked: Boolean,
    var goals: ArrayList<YAPForYouGoal>? = null,
    val lastUpdated: Date = Date(0),
    val achievementImage: Int? = null,
    val achievementStatusIcon: Int? = null
)  {
    val isCompleted: Boolean get() = completedPercentage == 100
}
