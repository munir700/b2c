package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.interfaces.IDashboard
import co.yap.modules.dashboard.states.DashBoardState
import co.yap.yapcore.BaseViewModel

class DashBoardViewModel(application: Application) :
    BaseViewModel<IDashboard.State>(application), IDashboard.ViewModel {

    override val state: IDashboard.State = DashBoardState()



}