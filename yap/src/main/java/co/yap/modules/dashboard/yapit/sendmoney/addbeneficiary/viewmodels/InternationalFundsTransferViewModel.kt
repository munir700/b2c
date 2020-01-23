package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.InternationalFundsTransferState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RMTTransactionRequestDTO
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.requestdtos.RxListRequest
import co.yap.networking.transactions.requestdtos.SwiftTransactionRequestDTO
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
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
    private val messagesRepository: MessagesRepository = MessagesRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList> =
        ArrayList()
    override val populateSpinnerData: MutableLiveData<ArrayList<InternationalFundsTransferReasonList.ReasonList>> =
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
                        listItemRemittanceFee = response.data.data!!.tierRateDTOList!!
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

    override fun rmtTransferRequest(beneficiaryId: String?) {
        launch {
            state.loading = true
            when (val response =
                mTransactionsRepository.rmtTransferRequest(
                    RMTTransactionRequestDTO(
                        state.fxRateAmount?.toDouble(),
                        state.fromFxRateCurrency,
                        state.reasonTransferCode,
                        beneficiaryId,
                        state.transactionNote,
                        state.reasonTransferValue
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    state.referenceNumber = response.data.data
                    clickEvent.postValue(Constants.ADD_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun swiftTransferRequest(beneficiaryId: String?) {
        launch {
            state.loading = true
            when (val response =
                mTransactionsRepository.swiftTransferRequest(
                    SwiftTransactionRequestDTO(
                        beneficiaryId,
                        state.fxRateAmount?.toDouble(),
                        0.0,
                        state.reasonTransferCode,
                        state.reasonTransferValue,
                        state.transactionNote,
                        state.rate
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    state.referenceNumber = response.data.data
                    clickEvent.postValue(Constants.ADD_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
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
                        "${response.data.data.value?.amount} ${state.fromFxRateCurrency}"
                    state.toFxRateCurrency = response.data.data.toCurrencyCode
                    state.toFxRate =
                        "${response.data.data.fxRates?.get(0)?.convertedAmount} ${state.toFxRateCurrency}"
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

    override fun createOtp(id: Int) {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(
                    createOtpGenericRequest = CreateOtpGenericRequest(
                        otpAction.toString()
                    )
                )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(200)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

}
