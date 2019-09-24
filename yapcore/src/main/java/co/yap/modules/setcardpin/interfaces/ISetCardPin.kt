package co.yap.modules.setcardpin.interfaces

import android.text.TextWatcher
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISetCardPin {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun loadData()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_SET_CARD_PIN_SUCCESS: Int
            get() = 1

        var pincode: String
        val clickEvent: SingleClickEvent
        fun handlePressOnNextButton(id: Int)
        fun setCardPin(cardSerialNumber: String)
        fun changeCardPinRequest(
            oldPin: String,
            newPin: String,
            confirmPin: String,
            cardSerialNumber: String,
            id: Int
        )
    }

    interface State : IBase.State {
        var dialerError: String
        var pincode: String
        var valid: Boolean
        fun getTextWatcher(): TextWatcher
        var sequence: Boolean
        var similar: Boolean
        var titleSetPin: String
        var buttonTitle: String

        var oldPin: String
        var newPin: String
        var confirmPin: String
        var cardSerialNumber: String
    }
}