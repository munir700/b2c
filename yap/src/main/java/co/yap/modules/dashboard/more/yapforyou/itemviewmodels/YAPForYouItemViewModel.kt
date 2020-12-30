package co.yap.modules.dashboard.more.yapforyou.itemviewmodels

import android.view.View
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.yapcore.interfaces.OnItemClickListener

class YAPForYouItemViewModel(
    val achievement: Y4YAchievementData,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, achievement, position)
    }
}