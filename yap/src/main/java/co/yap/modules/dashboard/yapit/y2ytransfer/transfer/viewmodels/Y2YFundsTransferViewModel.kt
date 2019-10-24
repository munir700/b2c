package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.states.Y2YFundsTransferState
import co.yap.modules.dashboard.yapit.y2ytransfer.viewmodels.Y2YBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class Y2YFundsTransferViewModel(application: Application) :
    Y2YBaseViewModel<IY2YFundsTransfer.State>(application),
    IY2YFundsTransfer.ViewModel {
    override val state: Y2YFundsTransferState = Y2YFundsTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
    }

    override fun handlePressOnView(id: Int) {
        if (state.checkValidity() == "") {
            clickEvent.postValue(id)
        } else {
            errorEvent.postValue(id)
        }
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_display_text_title))
        setRightButtonVisibility(false)
        setLeftButtonVisibility(true)
    }
}