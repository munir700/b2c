package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces.IY2YTransfer
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.states.Y2YTransferState
import co.yap.modules.dashboard.yapit.y2ytransfer.viewmodels.Y2YBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class Y2YTransferViewModel(application: Application) :
    Y2YBaseViewModel<IY2YTransfer.State>(application),
    IY2YTransfer.ViewModel {
    override val state: Y2YTransferState = Y2YTransferState(application)
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
        setToolBarTitle(getString(Strings.screen_yap_to_yap_transfer_text_header))
        setRightButtonVisibility(false)
    }
}