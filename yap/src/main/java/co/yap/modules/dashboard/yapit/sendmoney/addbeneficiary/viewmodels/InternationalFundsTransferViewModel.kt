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
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

class InternationalFundsTransferViewModel(application: Application) :
    SendMoneyBaseViewModel<IInternationalFundsTransfer.State>(application),
    IInternationalFundsTransfer.ViewModel,
    IRepositoryHolder<CustomersRepository> {


    private var mTransactionsRepository: TransactionsRepository = TransactionsRepository
    override val repository: CustomersRepository = CustomersRepository
    override val state: InternationalFundsTransferState = InternationalFundsTransferState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList> = ArrayList()
    override val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>> = MutableLiveData()
    private var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> =
        ArrayList()



    override fun handlePressOnNext(id: Int) {
        clickEvent.setValue(id)
    }


    override fun onCreate() {
        super.onCreate()
        transactionData.clear()
        getTransactionFeeInternational()
    }


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_international_funds_transfer_display_text_title))
        //toggleAddButtonVisibility(false)
    }


    /*
    * In this function get Remittance Transaction Fee.
    * */

    private fun getTransactionFeeInternational() {
        launch {
            state.loading = true
            val remittanceFeeRequestBody = RemittanceFeeRequest("PK", "")
            when (val response =
                /*TODO:For Swift (P011) and for cash pickup (P013) */
                mTransactionsRepository.getTransactionFeeWithProductCode(
                    "P013",
                    remittanceFeeRequestBody
                )) {
                is RetroApiResponse.Success -> {
                    var totalAmount: Double
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
                        listItemRemittanceFee = response.data.data!!.tierRateDTOList!!
                        state.listItemRemittanceFee = listItemRemittanceFee
                    }
                    getTransactionInternationalReasonList()
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
            // state.loading = false
        }
    }

    /*
    * In this function get All List of reasons.
    * */

    private fun getTransactionInternationalReasonList() {
        launch {
            state.loading = true
            when (val response =
                mTransactionsRepository.getTransactionInternationalReasonList("P012")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNullOrEmpty()) return@launch
                    response.data.data?.let {
                        transactionData.addAll(it.map {item->
                            InternationalFundsTransferReasonList.ReasonList(code = item.code?:"", reason = item.reason?:"")
                        })
                    }
                    getTransactionInternationalfxList()
                    populateSpinnerData.value = transactionData
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }


    /*
    * In this function get All List of reasons.
    * */

    private fun getTransactionInternationalfxList() {
        launch {

            /*TODO: SWIFT("P011"), RMT("P012"), CASH_PAYOUT("P013"),*/
            state.loading = true
            val rxListBody = RxListRequest("160")

            when (val response =
                mTransactionsRepository.getTransactionInternationalRXList("P013", rxListBody)) {
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


}