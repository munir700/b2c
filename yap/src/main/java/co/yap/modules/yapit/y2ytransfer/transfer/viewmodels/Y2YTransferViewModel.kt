package co.yap.modules.yapit.y2ytransfer.transfer.viewmodels

import android.app.Application
import co.yap.modules.yapit.y2ytransfer.transfer.interfaces.IY2YTransfer
import co.yap.modules.yapit.y2ytransfer.transfer.states.Y2YTransferState
import co.yap.modules.yapit.y2ytransfer.viewmodels.Y2YBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class Y2YTransferViewModel (application: Application) : Y2YBaseViewModel<IY2YTransfer.State>(application),
    IY2YTransfer.ViewModel {

    override val state: Y2YTransferState = Y2YTransferState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_yap_to_yap_transfer_text_header))
        setRightButtonVisibility(false)
    }
}