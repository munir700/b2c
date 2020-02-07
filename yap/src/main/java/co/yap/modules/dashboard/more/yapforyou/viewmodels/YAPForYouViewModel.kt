package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.modules.dashboard.more.main.viewmodels.MoreBaseViewModel
import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.states.YAPForYouState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class YAPForYouViewModel(application: Application) :
    MoreBaseViewModel<IYAPForYou.State>(application), IYAPForYou.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var selectedAchievement: Achievements= getAchievements().get(0)

    override val repository: CustomersRepository = CustomersRepository

    override val state: YAPForYouState =
        YAPForYouState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnBadge(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnBackButton() {

    }

    var BetterTogetherList: ArrayList<String> = arrayListOf(
        "Invite a friend",
        "Do a Y2Y transfer",
        "Split bills with friends",
        "Send money to someone outside YAP"
    )

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_yap_for_you_display_text_title))
        toggleAchievementsBadgeVisibility(parentViewModel!!.BadgeVisibility)

    }

    override fun onCreate() {
        super.onCreate()

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
                    "Invite a friend",
                    "Do a Y2Y transfer",
                    "Split bills with friends",
                    "Send money to someone outside YAP"
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
                arrayListOf(
                    "Invite a friend",
                    "Do a Y2Y transfer",
                    "Split bills with friends",
                    "Send money to someone outside YAP"
                )
            )
        )
        list.add(
            Achievements(
                4,
                "Take the leap",
                "0",
                ContextCompat.getColor(context, R.color.lightAqua),
                false,
                arrayListOf(
                    "Invite a friend",
                    "Do a Y2Y transfer",
                    "Split bills with friends",
                    "Send money to someone outside YAP"
                )
            )
        )
        list.add(
            Achievements(
                5,
                "YAP Store",
                "0",
                ContextCompat.getColor(context, R.color.lightPink),
                false,
                arrayListOf(
                    "Invite a friend",
                    "Do a Y2Y transfer",
                    "Split bills with friends",
                    "Send money to someone outside YAP"
                )
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