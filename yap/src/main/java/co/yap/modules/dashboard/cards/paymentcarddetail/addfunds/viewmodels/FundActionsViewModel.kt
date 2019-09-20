package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.states.FundActionsState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

open class FundActionsViewModel(application: Application) :
    BaseViewModel<IFundActions.State>(application), IFundActions.ViewModel {

    override val state: FundActionsState = FundActionsState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    override var error: String = ""


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