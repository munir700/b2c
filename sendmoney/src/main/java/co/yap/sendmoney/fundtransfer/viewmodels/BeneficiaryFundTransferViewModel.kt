package co.yap.sendmoney.fundtransfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.sendmoney.fundtransfer.interfaces.IBeneficiaryFundTransfer
import co.yap.sendmoney.fundtransfer.models.TransferFundData
import co.yap.sendmoney.fundtransfer.states.BeneficiaryFundTransferState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel


class BeneficiaryFundTransferViewModel(application: Application) :
    BaseViewModel<IBeneficiaryFundTransfer.State>(application = application),
    IBeneficiaryFundTransfer.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: BeneficiaryFundTransferState =
        BeneficiaryFundTransferState()
    override val repository: CustomersRepository = CustomersRepository
    override var errorEvent: MutableLiveData<String> = MutableLiveData()
    override var beneficiary: MutableLiveData<Beneficiary> = MutableLiveData()
    override var transferData: MutableLiveData<TransferFundData> = MutableLiveData()
    override var transactionThreshold: MutableLiveData<TransactionThresholdModel> =
        MutableLiveData()
    override var selectedPop: PurposeOfPayment? = null
    override var isCutOffTimeStarted: Boolean = false
    override var isSameCurrency: Boolean = false
    override var transactionWillHold: Boolean = false
    
    override fun onCreate() {
        super.onCreate()
        state.toolbarVisibility.set(true)
        state.toolbarTitle = getString(Strings.screen_cash_pickup_funds_display_text_header)
        state.leftIcon.set(true)
        state.rightButtonText = getString(Strings.common_button_cancel)
        state.rightIcon.set(true)
    }

}
