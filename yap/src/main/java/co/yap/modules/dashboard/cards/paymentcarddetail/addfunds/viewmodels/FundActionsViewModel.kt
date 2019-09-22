package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.states.FundActionsState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.AddFundsRequest
import co.yap.networking.transactions.requestdtos.RemoveFundsRequest
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

open class FundActionsViewModel(application: Application) :
    BaseViewModel<IFundActions.State>(application), IFundActions.ViewModel {

    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val state: FundActionsState = FundActionsState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    override var error: String = ""
    override var cardSerialNumber: String = ""


    override fun denominationFirstAmountClick() {
        state.amount = ""
        if (state.denominationFirstAmount.contains("+")) {
            state.amount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationFirstAmount.replace(
                    "+",
                    ""
                )
            )
        }
    }

    override fun denominationSecondAmount() {
        state.amount = ""
        if (state.denominationSecondAmount.contains("+")) {
            state.amount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationSecondAmount.replace(
                    "+",
                    ""
                )
            )
        }

    }

    override fun addFunds() {
        launch {
            state.loading = true
            when (val response = transactionsRepository.addFunds(AddFundsRequest(state.amount.toString() ,cardSerialNumber))) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(EVENT_ADD_FUNDS_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun removeFunds() {
        launch {
            state.loading = true
            when (val response = transactionsRepository.removeFunds(RemoveFundsRequest(state.amount.toString() , cardSerialNumber))) {
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
            state.loading = true
            when (val response = transactionsRepository.getFundTransferLimits(productCode)) {
                is RetroApiResponse.Success -> {
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun getFundTransferDenominations(productCode: String) {
        launch {
            state.loading = true
            when (val response = transactionsRepository.getFundTransferDenominations(productCode)) {
                is RetroApiResponse.Success -> {
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun denominationThirdAmount() {
        state.amount = ""
        if (state.denominationThirdAmount.contains("+")) {
            state.amount = Utils.getFormattedCurrencyWithoutComma(
                state.denominationThirdAmount.replace(
                    "+",
                    ""
                )
            )
        }
    }


    override fun buttonClickEvent(id: Int) {
        if (state.checkValidity() == "") {
            clickEvent.postValue(id)
        } else {
            errorEvent.postValue(id)
        }
    }

    override fun crossButtonClickEvent(id: Int) {
        clickEvent.postValue(id)
    }


}