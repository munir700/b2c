package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.adapters.AchievementTaskAdaptor
import co.yap.modules.dashboard.more.yapforyou.interfaces.IAchievementDetail
import co.yap.modules.dashboard.more.yapforyou.states.AchievementDetailState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class AchievementDetailViewModel(application: Application) :
    YapForYouBaseViewModel<IAchievementDetail.State>(application),
    IAchievementDetail.ViewModel {

    override val state: AchievementDetailState = AchievementDetailState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val adapter: AchievementTaskAdaptor = AchievementTaskAdaptor(mutableListOf())
    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        parentViewModel?.state?.toolbarTitle =
            getString(Strings.screen_yap_for_you_display_text_title)
        parentViewModel?.state?.leftIcon?.set(R.drawable.ic_close_primary)
        parentViewModel?.state?.rightIcon?.set(-1)
    }

    override fun onCreate() {
        super.onCreate()
        state.achievementIcon.set(getAchievementDetailIcon(parentViewModel?.selectedPosition))
        parentViewModel?.achievement?.features?.let {
            adapter.setList(it)
        }
    }

    private fun getAchievementDetailIcon(selectedPosition: Int?): Int {
        return when (selectedPosition) {
            0 -> R.drawable.ic_badge_dark_purple
            1 -> R.drawable.ic_badge_dark_blue
            2 -> R.drawable.ic_badge_dark_peach
            3 -> R.drawable.ic_badge_dark_green
            4 -> R.drawable.ic_badge_dark_pink
            5 -> R.drawable.ic_badge_dark_green
            else -> R.drawable.ic_badge_dark_grey
        }
    }

}