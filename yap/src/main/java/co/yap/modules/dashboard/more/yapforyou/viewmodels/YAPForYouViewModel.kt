package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.Achievement
import co.yap.modules.dashboard.more.yapforyou.AchievementTask
import co.yap.modules.dashboard.more.yapforyou.AchievmentIcons
import co.yap.modules.dashboard.more.yapforyou.CategoriesIcon
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
        parentViewModel?.achievement = getAchievements().get(0)

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


    fun getAchievmentIcons(color: Int, percent: Double): AchievmentIcons? {

        var categoriesIcon: CategoriesIcon = CategoriesIcon(percent, color.toString())

        return categoriesIcon.achievmentIcons
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
                ),
                icon = getAchievmentIcons(R.color.colorDarkPurple, 100.00)?.roundBadgeIcon,
                achievmentIcons = getAchievmentIcons(R.color.colorDarkPurple, 00.00)

            )
        )
        list.add(
            Achievement(
                name = "Up and running",
                colorCode = "ffddee",
                percentage = 100.00,
                feature = tasksList,
                icon = getAchievmentIcons(R.color.colorDarkBlue, 100.00)?.roundBadgeIcon,
                achievmentIcons = getAchievmentIcons(R.color.colorDarkBlue, 00.00)

            )
        )
        list.add(
            Achievement(
                name = "Better together",
                colorCode = "ffc430",
                percentage = 75.00,
                feature = tasksList,
                icon = getAchievmentIcons(R.color.colorDarkPeach, 75.00)?.roundBadgeIcon,
                achievmentIcons = getAchievmentIcons(R.color.colorDarkPeach, 75.00)

            )
        )
        list.add(
            Achievement(
                name = "Take the leap",
                colorCode = "ffddee",
                percentage = 00.00,
                feature = tasksList,
                icon = getAchievmentIcons(R.color.colorDarkPink, 00.00)?.roundBadgeIcon,
                achievmentIcons = getAchievmentIcons(R.color.colorDarkPink, 00.00)
            )
        )
        list.add(
            Achievement(
                name = "YAP Store",
                colorCode = "ffddee",
                percentage = 00.00,
                feature = tasksList,
                icon = getAchievmentIcons(R.color.colorDarkAqua, 00.00)?.roundBadgeIcon,
                achievmentIcons = getAchievmentIcons(R.color.colorDarkAqua, 00.00)

            )
        )
        list.add(
            Achievement(
                name = "You’re a Pro!",
                colorCode = "ffddee",
                percentage = 00.00,
                feature = tasksList,
                icon = getAchievmentIcons(R.color.colorDarkGrey, 00.00)?.roundBadgeIcon,
                achievmentIcons = getAchievmentIcons(R.color.colorDarkGrey, 00.00)

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