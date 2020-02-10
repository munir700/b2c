package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.AchievementTask
import co.yap.modules.dashboard.more.yapforyou.Achievements
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


    var BetterTogetherList: ArrayList<AchievementTask> = arrayListOf(
        AchievementTask("Invite a friend", false),
        AchievementTask("Do a Y2Y transfer", true),
        AchievementTask("Split bills with friends", false),
        AchievementTask("Send money to someone outside YAP", true)
    )

    override fun onResume() {
        super.onResume()
        parentViewModel?.state?.toolbarVisibility?.set(true)
        parentViewModel?.state?.toolbarTitle =
            getString(Strings.screen_yap_for_you_display_text_title)
        parentViewModel?.state?.leftIcon?.set(R.drawable.ic_back_arrow_left)
        parentViewModel?.state?.rightIcon?.set(R.drawable.ic_trade)
        parentViewModel?.achievement = getAchievements()[0].copy()
        parentViewModel?.achievement?.percentage =
            getString(Strings.screen_yap_for_you_display_text_completed_percentage).format(
                "${parentViewModel?.achievement?.percentage.toString()}%"
            )
        state.selectedAchievementPercentage = parentViewModel?.achievement?.percentage
        state.selectedAchievementTitle = parentViewModel?.achievement?.title ?: ""

    }


    override fun getAchievements(): MutableList<Achievements> {
        val list = mutableListOf<Achievements>()
        list.add(
            Achievements(
                1,
                "Get started",
                "100",
                ContextCompat.getColor(context, R.color.colorPrimaryAltHouseHold),
                R.drawable.ic_trade,
                true,
                arrayListOf(
                    AchievementTask("Invite a friend", true),
                    AchievementTask("Do a Y2Y transfer", true),
                    AchievementTask("Split bills with friends", false),
                    AchievementTask("Send money to someone outside YAP", true)
                )
            )
        )
        list.add(
            Achievements(
                2,
                "Up and running",
                "100",
                ContextCompat.getColor(context, R.color.colorBlue),
                R.drawable.ic_trade,
                true,
                BetterTogetherList
            )
        )
        list.add(
            Achievements(
                3,
                "Better together",
                "75",
                ContextCompat.getColor(context, R.color.lightYellow),
                R.drawable.path,
                false,
                BetterTogetherList
            )
        )
        list.add(
            Achievements(
                4,
                "Take the leap",
                "0",
                ContextCompat.getColor(context, R.color.lightAqua),
                R.drawable.path,
                false,
                BetterTogetherList
            )
        )
        list.add(
            Achievements(
                5,
                "YAP Store",
                "0",
                ContextCompat.getColor(context, R.color.lightPink),
                R.drawable.path,
                false,
                BetterTogetherList
            )
        )
        list.add(
            Achievements(
                6,
                "You’re a Pro!",
                null,
                ContextCompat.getColor(context, R.color.colorDisabledBtn),
                R.drawable.path,
                null,
                null
            )
        )

        return list
    }

}