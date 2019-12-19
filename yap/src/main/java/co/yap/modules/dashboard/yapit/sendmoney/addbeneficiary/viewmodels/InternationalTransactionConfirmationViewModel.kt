package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalTransactionConfirmation
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.InternationalTransactionConfirmationState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class InternationalTransactionConfirmationViewModel(application: Application) :
    BaseViewModel<IInternationalTransactionConfirmation.State>(application),
    IInternationalTransactionConfirmation.ViewModel {

    override val state: InternationalTransactionConfirmationState =
        InternationalTransactionConfirmationState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()


    override fun handlePressOnButtonClick(id: Int) {
        clickEvent.postValue(id)
    }

}