package co.yap.modules.dashboard.more.yapforyou.itemviewmodels

import android.view.View
import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.yapcore.interfaces.OnItemClickListener

class YAPForYouItemViewModel(
    val achievements: Achievements,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, achievements, position)
    }
}