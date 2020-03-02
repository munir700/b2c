package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.InternationalFundsTransferState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.requestdtos.RxListRequest
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils

class InternationalFundsTransferViewModel(application: Application) :
    SendMoneyBaseViewModel<IInternationalFundsTransfer.State>(application),
    IInternationalFundsTransfer.ViewModel,
    IRepositoryHolder<CustomersRepository> {


    private var mTransactionsRepository: TransactionsRepository = TransactionsRepository
    override var otpAction: String? = null
    override val repository: CustomersRepository = CustomersRepository
    override val state: InternationalFundsTransferState =
        InternationalFundsTransferState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList> =
        ArrayList()
    override val populateSpinnerData: MutableLiveData<ArrayList<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()
    override val transactionThreshold: MutableLiveData<TransactionThresholdModel> =
        MutableLiveData()
    private var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> =
        ArrayList()
    override var reasonPosition: Int = 0

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        state.setSpanable(0.0)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_international_funds_transfer_display_text_title))
    }

    override fun getTransactionFeeInternational(productCode: String?) {
        launch {
            //state.loading = true
            val remittanceFeeRequestBody = RemittanceFeeRequest(state.beneficiaryCountry, "")
            when (val response =
                mTransactionsRepository.getTransactionFeeWithProductCode(
                    productCode,
                    remittanceFeeRequestBody
                )) {
                is RetroApiResponse.Success -> {
                    state.feeType = response.data.data?.feeType
                    // state.totalAmount: Double
                    if (state.feeType == Constants.FEE_TYPE_FLAT) {
                        val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                        val feeAmountVAT = response.data.data?.tierRateDTOList?.get(0)?.vatAmount
                        if (feeAmount != null) {
                            state.totalAmount = feeAmount + feeAmountVAT!!
                            state.transferFee =
                                getString(Strings.screen_international_funds_transfer_display_text_fee).format(
                                    "AED",
                                    Utils.getFormattedCurrency(state.totalAmount.toString())
                                )
                            state.transferFeeSpannable =
                                Utils.getSppnableStringForAmount(
                                    context,
                                    state.transferFee,
                                    "AED",
                                    Utils.getFormattedCurrencyWithoutComma(state.totalAmount.toString())
                                )
                        }

                    } else if (state.feeType == Constants.FEE_TYPE_TIER) {
                        listItemRemittanceFee =
                            response.data.data?.tierRateDTOList ?: mutableListOf()
                        state.listItemRemittanceFee = listItemRemittanceFee
                    }
                    //state.loading = false
                    //getTransactionInternationalReasonList(productCode)
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
            // state.loading = false
        }
    }


    override fun getTransactionInternationalfxList(productCode: String?) {
        launch {
            state.loading = true
            val rxListBody = RxListRequest(state.beneficiaryId)

            when (val response =
                mTransactionsRepository.getTransactionInternationalRXList(
                    productCode,
                    rxListBody
                )) {
                is RetroApiResponse.Success -> {
                    state.senderCurrency = response.data.data.fromCurrencyCode
                    state.receiverCurrency = response.data.data.toCurrencyCode
                    state.receiverCurrencyAmountFxRate = response.data.data.value?.amount
                    state.fromFxRateCurrency = response.data.data.fromCurrencyCode
                    state.fromFxRate =
                        "${state.fromFxRateCurrency} ${Utils.getFormattedCurrency(response.data.data.value?.amount)}"
                    state.toFxRateCurrency = response.data.data.toCurrencyCode
                    state.toFxRate =
                        "${state.toFxRateCurrency} ${Utils.getFormattedCurrency(response.data.data.fxRates?.get(0)?.convertedAmount)}"
                    state.rate = response.data.data.fxRates?.get(0)?.convertedAmount
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun getReasonList(productCode: String?) {
        launch {
            transactionData.clear()
            //            state.loading = true
            when (val response =
                mTransactionsRepository.getTransactionInternationalReasonList(productCode)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNullOrEmpty()) return@launch
                    response.data.data?.let {
                        transactionData.addAll(it.map { item ->
                            InternationalFundsTransferReasonList.ReasonList(
                                code = item.code ?: "",
                                reason = item.reason ?: ""
                            )
                        })
                    }
                    //getTransactionInternationalfxList(productCode)
                    populateSpinnerData.value = transactionData
                }
                is RetroApiResponse.Error -> {
//                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun getMoneyTransferLimits(productCode: String?) {
        launch {
            when (val response = mTransactionsRepository.getFundTransferLimits(productCode)) {
                is RetroApiResponse.Success -> {
                    state.maxLimit = response.data.data?.maxLimit?.toDouble() ?: 0.00
                    state.minLimit = response.data.data?.minLimit?.toDouble() ?: 0.00
                    getCountryLimits()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }

    }

    override fun getCountryLimits() {
        launch {
            when (val response = repository.getCountryTransactionLimits(
                state.beneficiary?.country ?: "",
                state.beneficiary?.currency ?: ""
            )) {
                is RetroApiResponse.Success -> {
                    if (response.data.data?.toDouble() ?: 0.0 > 0.0) {
                        state.maxLimit = response.data.data?.toDouble()
                        if (response.data.data?.toDouble() ?: 0.0 < state.minLimit?.toDouble() ?: 0.0) {
                            state.minLimit = 1.0
                        }
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }

    }

    override fun getTransactionThresholds1() {
        launch {
            when (val response = mTransactionsRepository.getTransactionThresholds()) {
                is RetroApiResponse.Success -> {
                    transactionThreshold.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

}
