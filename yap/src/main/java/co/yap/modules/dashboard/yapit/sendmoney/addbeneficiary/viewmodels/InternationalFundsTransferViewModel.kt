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


    var mTransactionsRepository: TransactionsRepository = TransactionsRepository

    override val repository: CustomersRepository = CustomersRepository
    override val state: InternationalFundsTransferState = InternationalFundsTransferState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList> =
        ArrayList()
    override val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()
    var listItemSelectedCart: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> = ArrayList()



    override fun handlePressOnNext(id: Int) {
        clickEvent.setValue(id)
    }


    override fun onCreate() {
        super.onCreate()
        transactionData.clear()
        getTransactionFeeInternational()
        //getTransactionInternationalReasonList()
        getTransactionInternationalRxList()
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

                    val data_ =response.data.data
                    var totalAmount = 0.0
                    if (response.data.data?.feeType == "FLAT") {

                        val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                        val feeAmountVAT = response.data.data?.tierRateDTOList?.get(0)?.vatAmount

                        if (feeAmount != null) {
                            totalAmount = feeAmount + feeAmountVAT!!
                        }


                    } else if (response.data.data?.feeType == "TIER") {
                        listItemSelectedCart = response.data.data!!.tierRateDTOList!!
                        val remittanceTierFee = findFee(1001.0, listItemSelectedCart)
                        if (remittanceTierFee != null) {
                            if (remittanceTierFee.isNotEmpty()) {
                                val feeAmount = remittanceTierFee[0].feeAmount
                                val feeAmountVAT = remittanceTierFee[0]?.vatAmount
                                if (feeAmount != null) {
                                    totalAmount = feeAmount + feeAmountVAT!!
                                }
                            }
                        }
                    } else {
                        totalAmount = 0.0
                    }

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

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    private fun findFee(
        value: Double,
        tierRateDTOList: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>?
    ): List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>? {
        return tierRateDTOList?.filter { item -> item.amountFrom!! <= value && item.amountTo!! >= value }
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
                    populateSpinnerData.value = transactionData
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }


    /*
    * In this function get All List of reasons.
    * */

    private fun getTransactionInternationalRxList() {
        launch {

            /*TODO: SWIFT("P011"), RMT("P012"), CASH_PAYOUT("P013"),*/
            state.loading = true
            val rxListBody = RxListRequest("135")

            when (val response =
                mTransactionsRepository.getTransactionInternationalRXList("P013", rxListBody)) {
                is RetroApiResponse.Success -> {
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
            state.loading = false
        }
    }


}