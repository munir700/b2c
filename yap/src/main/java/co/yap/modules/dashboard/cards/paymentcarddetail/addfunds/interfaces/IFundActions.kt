package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IFundActions {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun buttonClickEvent(id: Int)
        fun denominationFirstAmountClick()
        fun denominationSecondAmount()
        fun denominationThirdAmount()
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        var error: String
    }

    interface State : IBase.State {
        var toolBarHeader: String
        var cardName: String
        var cardNumber: String
        var enterAmountHeading: String
        var currencyType: String
        var amount: String?
        var denominationFirstAmount: String
        var denominationSecondAmount: String
        var denominationThirdAmount: String
        var availableBalanceGuide: String
        var availableBalance: String
        var availableBalanceText: String
        var buttonTitle: String
        var valid: Boolean
        var maxLimit: Double
        var minLimit: Double
        var amountBackground: Drawable?
        var errorDescription: String
    }
}