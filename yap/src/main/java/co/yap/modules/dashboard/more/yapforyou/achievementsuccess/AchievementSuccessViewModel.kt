package co.yap.modules.dashboard.more.yapforyou.achievementsuccess

import android.app.Application
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YapForYouBaseViewModel

class AchievementSuccessViewModel(application: Application) :
    YapForYouBaseViewModel<IAchievementSuccess.State>(application = application),
    IAchievementSuccess.ViewModel {
    override val state: AchievementSuccessState = AchievementSuccessState()
}