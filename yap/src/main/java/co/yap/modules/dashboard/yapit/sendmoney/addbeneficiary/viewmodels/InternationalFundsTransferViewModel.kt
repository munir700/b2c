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
import co.yap.networking.transactions.RMTTransactionRequestDTO
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.requestdtos.RxListRequest
import co.yap.networking.transactions.requestdtos.UAEFTSTransactionRequestDTO
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryProductCode
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
    override val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()

    override fun handlePressOnButton(id: Int) {
        createOtp(id = id)
    }

    var listItemSelectedCart: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> = ArrayList()

    override fun onCreate() {
        super.onCreate()
        transactionData.clear()
    }


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_international_funds_transfer_display_text_title))
        //toggleAddButtonVisibility(false)
    }


    /*
    * In this function get Remittance Transaction Fee.
    * */

    override fun getTransactionFeeInternational(productCode: String?) {
        launch {
            state.loading = true
            val remittanceFeeRequestBody = RemittanceFeeRequest(state.beneficiaryCountry, "")
            when (val response =
                mTransactionsRepository.getTransactionFeeWithProductCode(
                    productCode,
                    remittanceFeeRequestBody
                )) {
                is RetroApiResponse.Success -> {
                    var totalAmount = 0.0
                    if (response.data.data?.feeType == "FLAT") {
                        val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                        val feeAmountVAT = response.data.data?.tierRateDTOList?.get(0)?.vatAmount
                        if (feeAmount != null) {
                            totalAmount = feeAmount + feeAmountVAT!!
                            state.transferFee =
                                getString(Strings.screen_international_funds_transfer_display_text_fee).format(
                                    "AED",
                                    Utils.getFormattedCurrency(totalAmount.toString())
                                )
                            state.transferFeeSpannable =
                                Utils.getSppnableStringForAmount(
                                    context,
                                    state.transferFee,
                                    "AED",
                                    Utils.getFormattedCurrencyWithoutComma(totalAmount.toString())
                                )
                        }

                    } else if (response.data.data?.feeType == "TIER") {
                        listItemSelectedCart = response.data.data!!.tierRateDTOList!!
                        state.listItemSelectedCart = listItemSelectedCart
                    }
                    getTransactionInternationalReasonList(productCode)
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
                        "25",
                        beneficiaryId,
                        state.fxRateAmount?.toDouble(),
                        state.beneficiaryCurrency,
                        ""
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(Constants.ADD_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    clickEvent.postValue(Constants.ADD_SUCCESS)
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
                    UAEFTSTransactionRequestDTO(
                        beneficiaryId,
                        state.fxRateAmount?.toDouble(),
                        0.0,
                        "51",
                        "dsdsdsds",
                        "",
                        ""
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(Constants.ADD_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    clickEvent.postValue(Constants.ADD_SUCCESS)
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }


    /*
    * In this function get All List of reasons.
    * */

    private fun getTransactionInternationalReasonList(productCode: String?) {
        launch {
            state.loading = true
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
                    getTransactionInternationalfxList(productCode)
                    populateSpinnerData.value = transactionData
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
            //state.loading = false
        }
    }


    /*
    * In this function get All List of reasons.
    * */

    private fun getTransactionInternationalfxList(productCode: String?) {
        launch {

            state.loading = true
            val rxListBody = RxListRequest(state.beneficiaryId)

            when (val response =
                mTransactionsRepository.getTransactionInternationalRXList(
                    productCode,
                    rxListBody
                )) {
                is RetroApiResponse.Success -> {
                    state.loading = false

                    println(response.data.data)
                    state.senderCurrency = response.data.data.fromCurrencyCode
                    state.receiverCurrency = response.data.data.toCurrencyCode
                    state.receiverCurrencyAmountFxRate = response.data.data.value?.amount

                    state.fromFxRateCurrency = response.data.data.fromCurrencyCode
                    state.fromFxRate =
                        "${response.data.data.value?.amount} ${state.fromFxRateCurrency}"


                    state.toFxRateCurrency = response.data.data.toCurrencyCode
                    state.toFxRate =
                        "${response.data.data.fxRates?.get(0)?.rate} ${state.toFxRateCurrency}"


                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    private fun createOtp(id: Int = 0) {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(
                    createOtpGenericRequest = CreateOtpGenericRequest(
                        otpAction.toString()
                    )
                )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(id)
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