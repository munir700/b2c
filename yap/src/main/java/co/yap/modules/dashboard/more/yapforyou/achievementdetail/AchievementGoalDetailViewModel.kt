package co.yap.modules.dashboard.more.yapforyou.achievementdetail

import android.app.Application
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YapForYouBaseViewModel
import co.yap.yapcore.SingleClickEvent

class AchievementGoalDetailViewModel(application: Application) :
    YapForYouBaseViewModel<IAchievementGoalDetail.State>(application = application),
    IAchievementGoalDetail.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: AchievementGoalDetailState = AchievementGoalDetailState()
    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

}
