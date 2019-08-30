package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.states.YapHomeState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent


class YapHomeViewModel(application: Application) : YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    override val transactionLogicHelper: TransactionLogicHelper = TransactionLogicHelper(context, this)

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}