package co.yap.modules.dashboard.more.yapforyou.models

import androidx.annotation.ColorInt

data class Y4YAchievementData(
    var title: String,
    var completedPercentage: Int,
    @ColorInt var tintColor: Int,
    var isLocked: Boolean,
    var tasks: ArrayList<YAPForYouGoal>? = null
) {
    val isCompleted: Boolean get() = completedPercentage == 100
}
