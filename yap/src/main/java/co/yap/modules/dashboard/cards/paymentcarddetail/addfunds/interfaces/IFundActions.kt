package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces

import android.graphics.drawable.Drawable
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IFundActions {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_ADD_FUNDS_SUCCESS: Int get() = 1
        val EVENT_REMOVE_FUNDS_SUCCESS: Int get() = 2
        fun buttonClickEvent(id: Int)
        fun crossButtonClickEvent(id: Int)
        fun denominationFirstAmountClick()
        fun denominationSecondAmount()
        fun denominationThirdAmount()
        fun addFunds()
        fun removeFunds()
        fun initateVM(topupCard: TopUpCard)
        fun getFundTransferLimits(productCode: String)
        fun getFundTransferDenominations(productCode: String)
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        val firstDenominationClickEvent: SingleClickEvent
        val secondDenominationClickEvent: SingleClickEvent
        val thirdDenominationClickEvent: SingleClickEvent
        fun createTransactionSession()
        var error: String
        var cardSerialNumber: String
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
        var denominationAmount: String

        var transactionFee: String
        var transactionFeeSpannableString: String?

        //success screen variables
        var topUpSuccess: String
        var primaryCardUpdatedBalance: String
        var spareCardUpdatedBalance: String
    }
}