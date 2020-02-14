package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.AchievmentIcons
import co.yap.modules.dashboard.more.yapforyou.CategoriesIcon
import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.states.YAPForYouState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class YAPForYouViewModel(application: Application) :
    YapForYouBaseViewModel<IYAPForYou.State>(application), IYAPForYou.ViewModel,
    IRepositoryHolder<TransactionsRepository> {

    override val repository: TransactionsRepository = TransactionsRepository
    override val state: YAPForYouState =
        YAPForYouState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var adaptor: YAPForYouAdapter = YAPForYouAdapter(mutableListOf())

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }


    override fun onResume() {
        super.onResume()
        setToolbarData()
    }

    private fun setToolbarData() {
        parentViewModel?.state?.toolbarVisibility?.set(true)
        parentViewModel?.state?.toolbarTitle =
            getString(Strings.screen_yap_for_you_display_text_title)
        parentViewModel?.state?.leftIcon?.set(R.drawable.ic_back_arrow_left)
        parentViewModel?.state?.rightIcon?.set(-1)
    }


    private fun setInitialAchievement() {
        parentViewModel?.achievement = parentViewModel?.achievements?.get(0)
        state.selectedAchievementPercentage =
            getString(Strings.screen_yap_for_you_display_text_completed_percentage).format("${parentViewModel?.achievement?.percentage}%")
        state.selectedAchievementTitle = parentViewModel?.achievement?.name ?: ""

    }

    fun getAchievmentIcons(color: Int, percent: Double): AchievmentIcons? {
        val categoriesIcon: CategoriesIcon = CategoriesIcon(percent, color.toString())
        return categoriesIcon.achievmentIcons
    }

//    fun getAchievements2(): MutableList<Achievement> {
//        val list = mutableListOf<Achievement>()
//        list.add(
//            Achievement(
//                name = "Get started",
//                colorCode = "ffddee",
//                percentage = 100.00,
//                feature = arrayListOf(
//                    AchievementTask("Invite a friend", true),
//                    AchievementTask("Do a Y2Y transfer", true),
//                    AchievementTask("Split bills with friends", false),
//                    AchievementTask("Send money to someone outside YAP", true)
//                ),
//                icon = getAchievmentIcons(R.color.colorDarkPurple, 100.00)?.roundBadgeIcon,
//                achievmentIcons = getAchievmentIcons(R.color.colorDarkPurple, 00.00)
//
//            )
//        )
//        list.add(
//            Achievement(
//                name = "Up and running",
//                colorCode = "ffddee",
//                percentage = 100.00,
//                feature = tasksList,
//                icon = getAchievmentIcons(R.color.colorDarkBlue, 100.00)?.roundBadgeIcon,
//                achievmentIcons = getAchievmentIcons(R.color.colorDarkBlue, 00.00)
//
//            )
//        )
//        list.add(
//            Achievement(
//                name = "Better together",
//                colorCode = "ffc430",
//                percentage = 75.00,
//                feature = tasksList,
//                icon = getAchievmentIcons(R.color.colorDarkPeach, 75.00)?.roundBadgeIcon,
//                achievmentIcons = getAchievmentIcons(R.color.colorDarkPeach, 75.00)
//
//            )
//        )
//        list.add(
//            Achievement(
//                name = "Take the leap",
//                colorCode = "ffddee",
//                percentage = 00.00,
//                feature = tasksList,
//                icon = getAchievmentIcons(R.color.colorDarkPink, 00.00)?.roundBadgeIcon,
//                achievmentIcons = getAchievmentIcons(R.color.colorDarkPink, 00.00)
//            )
//        )
//        list.add(
//            Achievement(
//                name = "YAP Store",
//                colorCode = "ffddee",
//                percentage = 00.00,
//                feature = tasksList,
//                icon = getAchievmentIcons(R.color.colorDarkAqua, 00.00)?.roundBadgeIcon,
//                achievmentIcons = getAchievmentIcons(R.color.colorDarkAqua, 00.00)
//
//            )
//        )
//        list.add(
//            Achievement(
//                name = "You’re a Pro!",
//                colorCode = "ffddee",
//                percentage = 00.00,
//                feature = tasksList,
//                icon = getAchievmentIcons(R.color.colorDarkGrey, 00.00)?.roundBadgeIcon,
//                achievmentIcons = getAchievmentIcons(R.color.colorDarkGrey, 00.00)
//
//            )
//        )
//
//        return list
//    }

    override fun getAchievements() {
        launch {
            state.loading = true
            when (val response = repository.getAchievements()) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.achievements =
                        response.data.data as MutableList<Achievement>
                    adaptor.setList(parentViewModel?.achievements ?: mutableListOf())
                    if (!response.data.data.isNullOrEmpty())
                        setInitialAchievement()

                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

//
//    private fun getSortAchievementList(val list: MutableList<Achievement>): MutableList<Achievement> {
//        val sortedAchievements = mutableListOf<Achievement>()
//        val sequencedList = listOf("Get started","Up and running","Better together",)
//
//        for(achievement in )
//
//    }
}
