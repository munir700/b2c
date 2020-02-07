package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.AchievementTask
import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.states.YAPForYouState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class YAPForYouViewModel(application: Application) :
    YapForYouBaseViewModel<IYAPForYou.State>(application), IYAPForYou.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: YAPForYouState =
        YAPForYouState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }


    var BetterTogetherList: ArrayList<AchievementTask> = arrayListOf(
        AchievementTask("Invite a friend", true),
        AchievementTask("Do a Y2Y transfer", true),
        AchievementTask("Split bills with friends", false),
        AchievementTask("Send money to someone outside YAP", true)
    )

    override fun onResume() {
        super.onResume()
        parentViewModel?.state?.toolbarTitle =
            getString(Strings.screen_yap_for_you_display_text_title)
        parentViewModel?.state?.leftIcon?.set(R.drawable.ic_back_arrow_left)
        parentViewModel?.state?.rightIcon?.set(R.drawable.ic_trade)

    }

    override fun getAchievements(): MutableList<Achievements> {
        val list = mutableListOf<Achievements>()
        list.add(
            Achievements(
                1,
                "Get started",
                "100",
                ContextCompat.getColor(context, R.color.colorPrimaryAlt),
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
                ContextCompat.getColor(context, R.color.colorSecondaryBlue),
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
                null,
                null
            )
        )

        return list
    }

}