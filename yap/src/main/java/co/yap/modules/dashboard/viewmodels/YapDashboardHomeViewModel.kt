package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.interfaces.IYapDashboardHome
import co.yap.modules.dashboard.states.YapDashboardHomeState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapDashboardHomeViewModel(application: Application) : BaseViewModel<IYapDashboardHome.State>(application),
    IYapDashboardHome.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapDashboardHomeState = YapDashboardHomeState()

}