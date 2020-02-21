package co.yap.modules.dashboard.transaction.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.TransactionDetails
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionCategory
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
    override var transactionId: String? = ""


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
                    setSenderOrReceiver(response.data.data)
                    spentVisibility(response.data.data)
                    state.transactionTitle = response.data.data?.title
                    if (response.data.data?.txnType != Constants.TRANSACTION_TYPE_CREDIT) {
                        state.spentTitle =
                            getString(Strings.screen_transaction_details_display_text_spent)
                    } else {
                        state.spentTitle =
                            getString(Strings.screen_transaction_details_display_text_received)
                    }
                    state.spentAmount =
                        response.data.data?.currency + " " + Utils.getFormattedCurrency(response.data.data?.amount.toString())
                    state.vatAmount = if (response.data.data?.vat != null) {
                        response.data.data?.currency + " " + Utils.getFormattedCurrency(response.data.data?.vat.toString())
                    } else {
                        "${response.data.data?.currency} ${Utils.getFormattedCurrency("0.00")}"
                    }
                    if (response.data.data?.postedFees != null)
                        state.feeAmount =
                            response.data.data?.currency + " " + Utils.getFormattedCurrency(response.data.data?.postedFees.toString()) else state.feeAmount =
                        response.data.data?.currency + " " + Utils.getFormattedCurrency("0.00")

                    if (response.data.data?.transactionNote != null && response.data.data?.transactionNote != "") {
                        state.addNoteTitle =
                            getString(Strings.screen_transaction_details_display_text_edit_note)
                        state.noteValue = response.data.data?.transactionNote
                    } else {
                        state.noteValue =
                            getString(Strings.screen_transaction_details_display_text_note_description)
                    }
                    if (response.data.data?.txnType == Constants.MANUAL_DEBIT) {
                        state.totalAmount =
                            "- ${Utils.getFormattedCurrency(response.data.data?.totalAmount.toString())}"
                    } else {
                        state.totalAmount =
                            Utils.getFormattedCurrency(response.data.data?.totalAmount.toString())
                    }
                    state.totalAmountCalculated =
                        "${response.data.data?.currency} ${Utils.getFormattedCurrency(response.data.data?.totalAmount.toString())}"

                    state.currency = response.data.data?.currency

                    /*state.totalAmount =
                        response.data.data?.currency + " " + Utils.getFormattedCurrency(response.data.data?.totalAmount.toString())*/
                    //val dateFormat = SimpleDateFormat("MMM dd, yyyy ・ HH:mmaa")
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

    private fun setSenderOrReceiver(data: TransactionDetails?) {
        data?.let {
            when (data.productCode) {
                Constants.Y_TO_Y_TRANSFER -> {
                    state.isYtoYTransfer.set(true)
                    state.transactionSender = data.senderName
                    state.transactionReceiver = data.receiverName
                }
            }
        }
    }

    private fun spentVisibility(data: TransactionDetails?) {
        data?.let {
            when (data.category) {
                TransactionCategory.SUPPORT_FEE.name -> {
                    state.spentVisibility.set(false)
                }
                else->{
                    state.spentVisibility.set(true)
                }
            }
        }
    }

}