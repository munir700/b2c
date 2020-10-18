package co.yap.modules.dashboard.cards.analytics.main.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class CardAnalyticsBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {

    var parentVM: ICardAnalyticsMain.ViewModel? = null
}