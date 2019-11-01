package co.yap.modules.dashboard.transaction.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {

    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    private var transactionRepository: TransactionsRepository = TransactionsRepository
    override var transactionId: String = ""


    override fun onCreate() {
        super.onCreate()
        getTransactionDetails()

        state.feeTitle = "Fee"
        state.totalTitle = "Total amount"
        state.addNoteTitle = "Add a note"
        state.noteValue = "Stay organized by adding transaction notes"

        state.feeAmount = Utils.getFormattedCurrency("0")
        /* state.totalAmount = Utils.getFormattedCurrency(
             addValues(
                 spentAmount = state.spentAmount,
                 feeAmount = state.feeAmount
             ).toString()*/
        //)
    }

    override fun handlePressOnBackButton(id: Int) {
        clickEvent.postValue(id)
    }

    override fun handlePressOnEditNoteClickEvent(id: Int) {
        clickEvent.postValue(id)
    }

    override fun handlePressOnShareButton(id: Int) {
        clickEvent.postValue(id)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTransactionDetails() {

        launch {
            state.loading = true
            when (val response = transactionRepository.getTransactionDetails(transactionId)) {
                is RetroApiResponse.Success -> {
                    //success
                    state.transactionTitle = response.data.data?.title
                    state.spentTitle = response.data.data?.txnType
                    state.spentAmount =
                        response.data.data?.currency + " " + Utils.getFormattedCurrency(response.data.data?.amount.toString())
                    if (response.data.data?.fee != null) state.feeAmount =
                        response.data.data?.currency + " " + response.data.data?.fee else state.feeAmount =
                        response.data.data?.currency + " " + "0.00"

                    state.totalAmount =
                        response.data.data?.currency + " " + Utils.getFormattedCurrency(response.data.data?.totalAmount.toString())
                    //val dateFormat = SimpleDateFormat("MMM dd, YYYY ・ HH:mmaa")
                    try {
                        val dateFormat = SimpleDateFormat("yyyy-MMM-dd'T'HH:mm")
                        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                        val date = dateFormat.parse(response.data.data?.creationDate)
                        state.toolBarTitle = dateFormat.format(date)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    //2018-11-23T01:01:01

                }

                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false

                }
            }
            state.loading = false
        }

    }

    private fun addValues(spentAmount: String = "", feeAmount: String = ""): Double {
        if (feeAmount != "") {
            return Utils.getFormattedCurrencyWithoutComma("1500.00").toDouble() + Utils.getFormattedCurrencyWithoutComma(
                "0.00"
            ).toDouble()
        }
        return 0.00

    }
}