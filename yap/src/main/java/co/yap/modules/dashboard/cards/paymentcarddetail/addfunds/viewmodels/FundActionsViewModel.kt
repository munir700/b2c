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
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.requestdtos.RemoveFundsRequest
import co.yap.networking.transactions.responsedtos.FundTransferDenominations
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.MyUserManager
import kotlinx.coroutines.delay

open class FundActionsViewModel(application: Application) :
    BaseViewModel<IFundActions.State>(application), IFundActions.ViewModel {

    override val htmlLiveData: MutableLiveData<String> = MutableLiveData()
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val state: FundActionsState = FundActionsState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    override val firstDenominationClickEvent: SingleClickEvent = SingleClickEvent()
    override val secondDenominationClickEvent: SingleClickEvent = SingleClickEvent()
    override val thirdDenominationClickEvent: SingleClickEvent = SingleClickEvent()
    override var error: String = ""
    override var cardSerialNumber: String = ""
    override var enteredAmount: MutableLiveData<String> = MutableLiveData()

    override val topUpTransactionModelLiveData: MutableLiveData<TopUpTransactionModel>? =
        MutableLiveData()

    override fun initateVM(topupCard: TopUpCard) {}
    override fun startPooling(showLoader: Boolean) {}
    override fun denominationFirstAmountClick() {
//        state.amount = ""
        if (state.denominationFirstAmount.contains("+")) {
            state.denominationAmount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationFirstAmount.replace(
                    "+",
                    ""
                )
            )
        } else if (state.denominationFirstAmount.contains("-")) {
            state.denominationAmount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationFirstAmount.replace(
                    "-",
                    ""
                )
            )
        }
        firstDenominationClickEvent.call()
    }

    override fun denominationSecondAmount() {
//        state.amount = ""
        state.denominationAmount = ""
        if (state.denominationSecondAmount.contains("+")) {
            state.denominationAmount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationSecondAmount.replace(
                    "+",
                    ""
                )
            )
        } else if (state.denominationSecondAmount.contains("-")) {
            state.denominationAmount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationSecondAmount.replace(
                    "-",
                    ""
                )
            )
        }
        secondDenominationClickEvent.call()
    }

    override fun denominationThirdAmount() {
//        state.amount = ""
        if (state.denominationThirdAmount.contains("+")) {
            state.denominationAmount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationThirdAmount.replace(
                    "+",
                    ""
                )
            )
        } else if (state.denominationThirdAmount.contains("-")) {
            state.denominationAmount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationThirdAmount.replace(
                    "-",
                    ""
                )
            )
        }
        thirdDenominationClickEvent.call()
    }

    override fun getFee(productCode: String) {
        launch {
            state.loading = true
            when (val response = transactionsRepository.getTransactionFeeWithProductCode(
                productCode, RemittanceFeeRequest()
            )) {
                is RetroApiResponse.Success -> {
                    if (response.data.data != null) {
                        if (response.data.data?.feeType == Constants.FEE_TYPE_FLAT) {
                            val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                            val VATAmount = response.data.data?.tierRateDTOList?.get(0)?.vatAmount
                            state.fee =
                                feeAmount?.plus(VATAmount ?: 0.0).toString().toFormattedCurrency()
                        }
                    } else {
                        state.fee = "0.0".toFormattedCurrency()
                    }
                    clickEvent.postValue(Constants.CARD_FEE)
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
                }
            }
            state.loading = false
        }
    }

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
                    MyUserManager.updateCardBalance()
                    delay(1000)
                    clickEvent.setValue(EVENT_ADD_FUNDS_SUCCESS)
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
            when (val response = transactionsRepository.getFundTransferLimits(productCode)) {
                is RetroApiResponse.Success -> {
                    state.maxLimit = response.data.data?.maxLimit?.toDouble() ?: 0.00
                    state.minLimit = response.data.data?.minLimit?.toDouble() ?: 0.00
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
                    var fundsType: String? = null
                    if (productCode == Constants.SUPP_CARD) {
                        fundsType = "+"
                    } else if (productCode == co.yap.modules.others.helper.Constants.REMOVE_FUNDS_PRODUCT_CODE) {
                        fundsType = "-"
                    } else {
                        fundsType = "+"
                    }

                    val sortedData =
                        response.data.data?.sortedWith(Comparator { s1: FundTransferDenominations, s2: FundTransferDenominations -> s1.amount.toInt() - s2.amount.toInt() })
                    if (sortedData?.size in 1..3) {
                        state.denominationFirstAmount =
                            fundsType + (sortedData?.get(0)?.amount ?: "")
                        state.denominationSecondAmount =
                            fundsType + (sortedData?.get(1)?.amount ?: "")
                        state.denominationThirdAmount =
                            fundsType + (sortedData?.get(2)?.amount ?: "")
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
        if (state.checkValidity("") == "") {
            clickEvent.postValue(id)
        } else {
            errorEvent.postValue(id)
        }
    }

    override fun crossButtonClickEvent(id: Int) {
        clickEvent.postValue(id)
    }

    override fun createTransactionSession() {

    }

}