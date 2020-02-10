package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.Achievement
import co.yap.modules.dashboard.more.yapforyou.AchievementTask
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.states.YAPForYouState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class YAPForYouViewModel(application: Application) :
    YapForYouBaseViewModel<IYAPForYou.State>(application), IYAPForYou.ViewModel {
    override val state: YAPForYouState =
        YAPForYouState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.achievements = getAchievements()
    }

    override fun onResume() {
        super.onResume()
        setToolbarData()
        setInitialAchievement()
    }

    private fun setToolbarData() {
        parentViewModel?.state?.toolbarVisibility?.set(true)
        parentViewModel?.state?.toolbarTitle =
            getString(Strings.screen_yap_for_you_display_text_title)
        parentViewModel?.state?.leftIcon?.set(R.drawable.ic_back_arrow_left)
        parentViewModel?.state?.rightIcon?.set(R.drawable.ic_trade)
    }

    private fun setInitialAchievement() {
        parentViewModel?.achievement = getAchievements()[0].copy()
        state.selectedAchievementPercentage =
            getString(Strings.screen_yap_for_you_display_text_completed_percentage).format("${parentViewModel?.achievement?.percentage}%")
        state.selectedAchievementTitle = parentViewModel?.achievement?.name ?: ""

    }

    override fun getAchievements(): MutableList<Achievement> {
        val list = mutableListOf<Achievement>()
        list.add(
            Achievement(
                name = "Get started",
                colorCode = "ffddee",
                percentage = 100.00,
                feature = arrayListOf(
                    AchievementTask("Invite a friend", true),
                    AchievementTask("Do a Y2Y transfer", true),
                    AchievementTask("Split bills with friends", false),
                    AchievementTask("Send money to someone outside YAP", true)
                )
            )
        )
        list.add(
            Achievement(
                name = "Up and running",
                colorCode = "ffddee",
                percentage = 100.00,
                feature = tasksList
            )
        )
        list.add(
            Achievement(
                name = "Better together",
                colorCode = "ffddee",
                percentage = 75.00,
                feature = tasksList
            )
        )
        list.add(
            Achievement(
                name = "Take the leap",
                colorCode = "ffddee",
                percentage = 00.00,
                feature = tasksList
            )
        )
        list.add(
            Achievement(
                name = "YAP Store",
                colorCode = "ffddee",
                percentage = 00.00,
                feature = tasksList
            )
        )
        list.add(
            Achievement(
                name = "You’re a Pro!",
                colorCode = "ffddee",
                percentage = 00.00,
                feature = tasksList
            )
        )
        return list
    }

    private var tasksList: ArrayList<AchievementTask> = arrayListOf(
        AchievementTask("Invite a friend", false),
        AchievementTask("Do a Y2Y transfer", true),
        AchievementTask("Split bills with friends", false),
        AchievementTask("Send money to someone outside YAP", true)
    )
}