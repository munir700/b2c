package co.yap.modules.dashboard.more.yapforyou.models

import androidx.annotation.ColorInt

data class Y4YAchievementData(
    var title: String,
    var completedPercentage: Int,
    @ColorInt var tintColor: Int,
    var isLocked: Boolean,
    var goals: ArrayList<YAPForYouGoal>? = null,
    val lastUpdated: String,
    val completeAchievementIcon : Int? = null,
    val incompleteAchievementIcon : Int? = null
)  {
    val isCompleted: Boolean get() = completedPercentage == 100
}
