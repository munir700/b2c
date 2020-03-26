package co.yap.sendMoney.fundtransfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer
import co.yap.sendMoney.fundtransfer.models.TransferFundData
import co.yap.sendMoney.fundtransfer.states.BeneficiaryFundTransferState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent


class BeneficiaryFundTransferViewModel(application: Application) :
    BaseViewModel<IBeneficiaryFundTransfer.State>(application = application),
    IBeneficiaryFundTransfer.ViewModel {
    override val state: BeneficiaryFundTransferState =
        BeneficiaryFundTransferState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: MutableLiveData<String> = MutableLiveData()
    override var beneficiary: MutableLiveData<Beneficiary> = MutableLiveData()
    override var transferData: MutableLiveData<TransferFundData> = MutableLiveData()
    override var transactionThreshold: MutableLiveData<TransactionThresholdModel> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        state.toolbarVisibility.set(true)
        state.toolBarTitle = getString(Strings.screen_cash_pickup_funds_display_text_header)
        state.leftIcon.set(true)
        state.rightButtonText = getString(Strings.common_button_cancel)
        state.rightIcon.set(true)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }


}
