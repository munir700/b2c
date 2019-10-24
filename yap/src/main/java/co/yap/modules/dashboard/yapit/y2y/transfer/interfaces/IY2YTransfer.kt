package co.yap.modules.dashboard.yapit.y2y.transfer.interfaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IY2YTransfer {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        fun handlePressOnView(id: Int)

    }

    interface State : IBase.State {
        var amountBackground: Drawable?
        var amount: String?
        var valid: Boolean
        var minLimit: Double
        var availableBalance: String
        var errorDescription: String
        var currencyType: String
        var maxLimit: Double
        var availableBalanceText: String
        var availableBalanceGuide: String
    }
}