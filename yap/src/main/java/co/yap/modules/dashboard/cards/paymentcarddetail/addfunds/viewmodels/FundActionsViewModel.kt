package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.states.FundActionsState
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.customers.responsedtos.beneficiary.TopUpTransactionModel
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.AddFundsRequest
import co.yap.networking.transactions.requestdtos.RemoveFundsRequest
import co.yap.networking.transactions.responsedtos.FundTransferDenominations
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.sendmoney.base.SMFeeViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getValueWithoutComa
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.delay

open class FundActionsViewModel(application: Application) :
    SMFeeViewModel<IFundActions.State>(application), IFundActions.ViewModel {

    override val htmlLiveData: MutableLiveData<String> = MutableLiveData()
    override val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val state: FundActionsState = FundActionsState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    override val firstDenominationClickEvent: SingleClickEvent = SingleClickEvent()
    override val secondDenominationClickEvent: SingleClickEvent = SingleClickEvent()
    override val thirdDenominationClickEvent: SingleClickEvent = SingleClickEvent()
    override var error: String = ""
    override var cardSerialNumber: String = ""
    override var enteredAmount: MutableLiveData<String> = MutableLiveData()
    override val transactionThreshold: MutableLiveData<TransactionThresholdModel> =
        MutableLiveData()


    override val topUpTransactionModelLiveData: MutableLiveData<TopUpTransactionModel>? =
        MutableLiveData()

    override fun initateVM(topupCard: TopUpCard) {}
    override fun startPooling(showLoader: Boolean) {}
    override fun getTransactionThresholds() {}

    override fun addFunds() {
        launch {
            state.loading = true
            when (val response = transactionsRepository.addFunds(
                AddFundsRequest(
                    state.amount.toString(),
                    cardSerialNumber
                )
            )) {
                is RetroApiResponse.Success -> {
                    SessionManager.updateCardBalance {}
                    delay(1000)
//                    clickEvent.setValue(EVENT_ADD_FUNDS_SUCCESS)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.postValue(2)
                    state.loading = false
                }
            }
        }
    }

    override fun removeFunds() {
        launch {
            state.loading = true
            when (val response = transactionsRepository.removeFunds(
                RemoveFundsRequest(
                    state.amount.toString(),
                    cardSerialNumber
                )
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(EVENT_REMOVE_FUNDS_SUCCESS)
                }

                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }


    override fun getFundTransferLimits(productCode: String) {
        launch {
            when (val response = transactionsRepository.getFundTransferLimits(
                productCode,
                SessionManager.user?.uuid
            )) {
                is RetroApiResponse.Success -> {
                    state.maxLimit = response.data.data?.maxLimit?.toDouble() ?: 0.00
                    state.minLimit = response.data.data?.minLimit?.toDouble() ?: 0.00
                    state.maximumAccumulative.set(
                        response.data.data?.dailyAccumulativeMaxLimit?.toDouble() ?: 0.00
                    )
                    val remaining = response.data.data?.remainingAvailableLimit
                    state.remainingAccumulative.set(
                        if (remaining?.contains("-") == true) 0.0 else remaining?.toDouble() ?: 0.00
                    )
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun getFundTransferDenominations(productCode: String) {
        launch {
            state.loading = true
            when (val response = transactionsRepository.getFundTransferDenominations(productCode)) {
                is RetroApiResponse.Success -> {
                    val fundsType: String = when (productCode) {
                        TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode -> {
                            "+"
                        }
                        TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> {
                            "-"
                        }
                        else -> {
                            "+"
                        }
                    }

                    val sortedData =
                        response.data.data?.sortedWith(Comparator { s1: FundTransferDenominations, s2: FundTransferDenominations -> s1.amount.toInt() - s2.amount.toInt() })
                    if (sortedData?.size in 1..3) {
                        state.denominationFirstAmount.set(
                            fundsType + (sortedData?.get(0)?.amount ?: "")
                        )
                        state.denominationSecondAmount.set(
                            fundsType + (sortedData?.get(1)?.amount ?: "")
                        )
                        state.denominationThirdAmount.set(
                            fundsType + (sortedData?.get(2)?.amount ?: "")
                        )
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }


    override fun buttonClickEvent(id: Int) {
        clickEvent.setValue(id)
    }

    override fun createTransactionSession() {

    }

    override fun denominationAmountValidator(amount: String, enable: (boolean: Boolean) -> Unit) {
        state.denominationAmount.set(
            Utils.getFormattedCurrencyWithoutComma(
                amount.replace(
                    if (amount.contains("+")) "+" else "-",
                    ""
                )
            )
        )
        if (!enteredAmount.value.equals(state.denominationAmount.get())) {
            enteredAmount.value = state.denominationAmount.get()
            enable.invoke(enteredAmount.value?.getValueWithoutComa().parseToDouble() <= state.remainingAccumulative.get()?:0.0)
        } else {
            enteredAmount.value = ""
            enable.invoke(false)
        }

    }
}