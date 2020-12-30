package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.adapters.AchievementTaskAdaptor
import co.yap.modules.dashboard.more.yapforyou.interfaces.IAchievement
import co.yap.modules.dashboard.more.yapforyou.states.AchievementState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class AchievementViewModel(application: Application) :
    YapForYouBaseViewModel<IAchievement.State>(application),
    IAchievement.ViewModel {

    override val state: AchievementState = AchievementState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val adapter: AchievementTaskAdaptor = AchievementTaskAdaptor(mutableListOf())
    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        setupToolbar()
        parentViewModel?.achievement?.tasks?.let {
            adapter.setList(it)
        }
    }

    private fun setupToolbar() {
        setToolBarTitle(getString(Strings.screen_yap_for_you_display_text_title))
        setLeftIcon(R.drawable.ic_close_primary)
        setLeftIconVisibility(true)
        toggleToolBarVisibility(true)
    }
}