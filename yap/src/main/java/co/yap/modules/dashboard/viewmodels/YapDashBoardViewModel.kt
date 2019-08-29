package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.interfaces.IYapDashboard
import co.yap.modules.dashboard.states.YapDashBoardState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapDashBoardViewModel(application: Application) :
    BaseViewModel<IYapDashboard.State>(application), IYapDashboard.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapDashBoardState = YapDashBoardState()

    override fun handlePressOnNavigationItem(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        populateState()
    }

    private fun populateState() {
        state.accountNo = "033 1234567890123456"
        state.ibanNo = "AE07 0331 2345 6789 0123 456"
        state.fullName = "Bilal Shabbir"
    }
}