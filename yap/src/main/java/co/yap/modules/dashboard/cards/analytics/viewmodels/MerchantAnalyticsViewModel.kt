package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.analytics.interfaces.IMerchantAnalytics
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.states.MerchantAnalyticsState

class MerchantAnalyticsViewModel(application: Application) :
    CardAnalyticsBaseViewModel<IMerchantAnalytics.State>(application = application),
    IMerchantAnalytics.ViewModel {
    override var parentViewModelA: ICardAnalyticsMain.ViewModel? = null
    override val state: MerchantAnalyticsState = MerchantAnalyticsState()

    override fun onCreate() {
        super.onCreate()
        parentViewModelA = parentViewModel
    }

}