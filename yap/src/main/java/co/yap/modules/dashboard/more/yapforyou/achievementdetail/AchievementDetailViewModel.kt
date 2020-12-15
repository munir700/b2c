package co.yap.modules.dashboard.more.yapforyou.achievementdetail

import android.app.Application
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YapForYouBaseViewModel
import co.yap.yapcore.SingleClickEvent

class AchievementDetailViewModel(application: Application) :
    YapForYouBaseViewModel<IAchievementDetail.State>(application = application),
    IAchievementDetail.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: AchievementDetailState = AchievementDetailState()

}
