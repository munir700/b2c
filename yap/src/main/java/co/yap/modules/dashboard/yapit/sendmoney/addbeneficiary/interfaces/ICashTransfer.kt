package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import android.graphics.drawable.Drawable
import android.text.SpannableString
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICashTransfer {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        var receiverUUID: String

    }

    interface State : IBase.State {
        var amountBackground: Drawable?
        var feeAmountString: SpannableString
        var amount: String
        var valid: Boolean
        var minLimit: Double
        var availableBalance: String?
        var errorDescription: String
        var currencyType: String
        var maxLimit: Double
        var availableBalanceText: String
        var availableBalanceGuide: String
        var fullName: String
        var noteValue: String
        var imageUrl: String
    }
}