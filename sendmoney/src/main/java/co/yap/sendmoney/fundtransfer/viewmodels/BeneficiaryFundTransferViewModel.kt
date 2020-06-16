package co.yap.sendmoney.fundtransfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.SMCoolingPeriodRequest
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.customers.responsedtos.sendmoney.SMCoolingPeriod
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.sendmoney.fundtransfer.interfaces.IBeneficiaryFundTransfer
import co.yap.sendmoney.fundtransfer.models.TransferFundData
import co.yap.sendmoney.fundtransfer.states.BeneficiaryFundTransferState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.parseToDouble


class BeneficiaryFundTransferViewModel(application: Application) :
    BaseViewModel<IBeneficiaryFundTransfer.State>(application = application),
    IBeneficiaryFundTransfer.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: BeneficiaryFundTransferState =
        BeneficiaryFundTransferState()
    override val repository: CustomersRepository = CustomersRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: MutableLiveData<String> = MutableLiveData()
    override var beneficiary: MutableLiveData<Beneficiary> = MutableLiveData()
    override var transferData: MutableLiveData<TransferFundData> = MutableLiveData()
    override var transactionThreshold: MutableLiveData<TransactionThresholdModel> = MutableLiveData()
    override var selectedPop: PurposeOfPayment? = null
    override var isSameCurrency: Boolean = false
    override var transactionWillHold: Boolean = false
    override var smCoolingPeriod: SMCoolingPeriod? = null
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

    override fun getCoolingPeriod(smCoolingPeriodRequest: SMCoolingPeriodRequest) {
        launch {
            when (val response = repository.getCoolingPeriod(smCoolingPeriodRequest)) {
                is RetroApiResponse.Success -> {
                    smCoolingPeriod = response.data.data
                }
                is RetroApiResponse.Error -> {
                    smCoolingPeriod = SMCoolingPeriod(
                        coolingPeriodDuration = "8",
                        maxAllowedCoolingPeriodAmount = "10000",
                        createdOn = "2020-06-09T09:45:47",
                        difference = 2030,
                        consumedAmount = 0.0,
                        beneficiaryId = smCoolingPeriodRequest.beneficiaryId,
                        productCode = smCoolingPeriodRequest.productCode
                    )
//                    state.toast = "${response.error.message}^${AlertType.DIALOG_WITH_FINISH.name}"
                }
            }
        }
    }

    override fun isInCoolingPeriod(): Boolean {
        smCoolingPeriod?.let { period ->
            val coolingPeriodDurationInSeconds =
                period.coolingPeriodDuration.parseToDouble().times(3600).toLong()
            return period.difference ?: 0 < coolingPeriodDurationInSeconds
        } ?: return false
    }

}
