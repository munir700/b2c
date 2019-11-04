package co.yap.modules.dashboard.transaction.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_INPUT
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_OUTPUT
import co.yap.yapcore.helpers.DateUtils.datetoString
import co.yap.yapcore.helpers.DateUtils.stringToDate
import co.yap.yapcore.helpers.Utils


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {

    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    private var transactionRepository: TransactionsRepository = TransactionsRepository
    override var transactionId: String = ""


    override fun onCreate() {
        super.onCreate()
        getTransactionDetails()

        state.feeTitle = getString(Strings.screen_transaction_details_display_text_fee)
        state.totalTitle = getString(Strings.screen_transaction_details_display_text_total_amount)
        state.addNoteTitle = getString(Strings.screen_transaction_details_display_text_add_note)
        state.noteValue =
            getString(Strings.screen_transaction_details_display_text_note_description)
        // state.feeAmount = Utils.getFormattedCurrency("0")
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

                    if (response.data.data?.transactionNote != null) {
                        state.addNoteTitle =
                            getString(Strings.screen_transaction_details_display_text_edit_note)
                        state.noteValue = response.data.data?.transactionNote
                    } else {
                        state.noteValue =
                            getString(Strings.screen_transaction_details_display_text_note_description)
                    }

                    state.totalAmount =
                        response.data.data?.currency + " " + Utils.getFormattedCurrency(response.data.data?.totalAmount.toString())
                    //val dateFormat = SimpleDateFormat("MMM dd, YYYY ・ HH:mmaa")
                    try {
                        val date =
                            stringToDate(response.data.data?.creationDate!!, FORMAT_LONG_INPUT)
                        state.toolBarTitle = datetoString(date, FORMAT_LONG_OUTPUT)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

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