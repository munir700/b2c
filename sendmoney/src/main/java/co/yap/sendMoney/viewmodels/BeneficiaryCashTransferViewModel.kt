package co.yap.sendmoney.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.sendmoney.interfaces.IBeneficiaryCashTransfer
import co.yap.sendmoney.states.BeneficiaryCashTransferState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent


class BeneficiaryCashTransferViewModel(application: Application) :
    BaseViewModel<IBeneficiaryCashTransfer.State>(application = application),
    IBeneficiaryCashTransfer.ViewModel {
    override val state: BeneficiaryCashTransferState = BeneficiaryCashTransferState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: MutableLiveData<String> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        state.toolBarVisibility = true
        state.toolBarTitle = getString(Strings.screen_cash_pickup_funds_display_text_header)
        state.leftButtonVisibility = true
        state.rightButtonText = getString(Strings.common_button_cancel)
        state.rightButtonVisibility = true

    }

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }


}
