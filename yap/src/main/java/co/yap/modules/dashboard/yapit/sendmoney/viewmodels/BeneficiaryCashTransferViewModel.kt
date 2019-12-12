package co.yap.modules.dashboard.yapit.sendmoney.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.IBeneficiaryCashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.states.BeneficiaryCashTransferState
import co.yap.yapcore.BaseViewModel


class BeneficiaryCashTransferViewModel(application: Application) :
    BaseViewModel<IBeneficiaryCashTransfer.State>(application = application),
    IBeneficiaryCashTransfer.ViewModel {
    override val state: BeneficiaryCashTransferState = BeneficiaryCashTransferState()

    override fun handlePressOnView(id: Int) {

    }

}