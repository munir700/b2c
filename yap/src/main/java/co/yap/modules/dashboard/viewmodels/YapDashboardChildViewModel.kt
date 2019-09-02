package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.interfaces.IYapDashboard
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class YapDashboardChildViewModel<S : IBase.State>(application: Application) : BaseViewModel<S>(application) {

    var parentViewModel: IYapDashboard.ViewModel? = null

}