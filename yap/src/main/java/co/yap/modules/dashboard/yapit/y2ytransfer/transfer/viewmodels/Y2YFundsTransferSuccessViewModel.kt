package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces.IY2YFundsTransferSuccess
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.states.Y2YFundsTransferSuccessState
import co.yap.modules.dashboard.yapit.y2ytransfer.viewmodels.Y2YBaseViewModel
import co.yap.translation.Strings

class Y2YFundsTransferSuccessViewModel(application: Application) :
    Y2YBaseViewModel<IY2YFundsTransferSuccess.State>(application),
    IY2YFundsTransferSuccess.ViewModel {

    override val state: Y2YFundsTransferSuccessState = Y2YFundsTransferSuccessState()

    override fun handlePressOnDashboardButton(id: Int) {

    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_success_display_text_title))
        setRightButtonVisibility(false)
        setLeftButtonVisibility(false)
    }
}