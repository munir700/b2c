package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.adapters.CompletedAchievementsAdaptor
import co.yap.modules.dashboard.more.yapforyou.interfaces.ICompletedAchievements
import co.yap.modules.dashboard.more.yapforyou.states.CompletedAchievementsState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class CompletedAchievementsViewModel(application: Application) :
    YapForYouBaseViewModel<ICompletedAchievements.State>(application),
    ICompletedAchievements.ViewModel {
    override val state: CompletedAchievementsState = CompletedAchievementsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val adapter: CompletedAchievementsAdaptor =
        CompletedAchievementsAdaptor(mutableListOf())

    override fun onCreate() {
        super.onCreate()
        val list = parentViewModel?.achievements ?: mutableListOf()
        adapter.setList(list)
//        list.forEach {
////            it.percentage =
////                getString(Strings.screen_yap_for_you_display_text_completed_percentage).format(
////                    "${it.percentage.toString()}%"
////                )
////        }

    }

    override fun onResume() {
        super.onResume()
        parentViewModel?.state?.toolbarTitle =
            getString(Strings.screen_your_achievements_display_text_toolbar_title)
        parentViewModel?.state?.leftIcon?.set(R.drawable.ic_back_arrow_left)
        parentViewModel?.state?.rightIcon?.set(-1)
    }

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }
}