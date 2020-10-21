package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.analytics.interfaces.ICategoryAnalytics
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.states.CategoryAnalyticsState

class CategoryAnalyticsViewModel(application: Application) :
    CardAnalyticsBaseViewModel<ICategoryAnalytics.State>(application),
    ICategoryAnalytics.ViewModel {
    override val state: CategoryAnalyticsState = CategoryAnalyticsState()
    override lateinit var pViewModel: ICardAnalyticsMain.ViewModel

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.let {
            pViewModel = it
        }
    }
}