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
        parentViewModel?.state?.rightIcon?.set(R.drawable.ic_trade)
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

    override fun getAchievements() {
        launch {
            state.loading = true
            when (val response = repository.getAchievements()) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.achievements =
                        response.data.data as MutableList<Achievement>
                    achievementDataFactory()
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

    private fun achievementDataFactory(){
        var position = 0
        for(achievement in parentViewModel?.achievements?: mutableListOf()){
            achievement.also {
                it.icon = getAchievementIcon(position)
                position++
            }
        }
    }

    override fun getAchievementIcon(position: Int,isWithBadged:Boolean): Int {
        return when (position) {
            0 -> if(!isWithBadged) R.drawable.ic_round_badge_dark_purple else R.drawable.ic_badge_dark_purple
            1 -> if(!isWithBadged) R.drawable.ic_round_badge_dark_blue else R.drawable.ic_badge_dark_blue
            2 -> if(!isWithBadged) R.drawable.ic_round_badge_light_peach else R.drawable.ic_badge_light_peach
            3 -> if(!isWithBadged) R.drawable.lock else R.drawable.lock
            4 -> if(!isWithBadged) R.drawable.lock else R.drawable.lock
            5 -> if(!isWithBadged) R.drawable.lock else R.drawable.lock
            else -> R.drawable.ic_round_badge_dark_grey
        }
    }
}
