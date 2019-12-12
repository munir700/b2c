package co.yap.modules.dashboard.yapit.sendmoney.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.IBeneficiaryCashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.states.BeneficiaryCashTransferState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent


class BeneficiaryCashTransferViewModel(application: Application) :
    BaseViewModel<IBeneficiaryCashTransfer.State>(application = application),
    IBeneficiaryCashTransfer.ViewModel {
    override val state: BeneficiaryCashTransferState = BeneficiaryCashTransferState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.toolBarVisibility = true
        state.toolBarTitle = "Send money"
        state.leftButtonVisibility = true
        state.rightButtonVisibility = true

    }

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }


}
