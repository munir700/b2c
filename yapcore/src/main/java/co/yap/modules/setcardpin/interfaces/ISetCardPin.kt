package co.yap.modules.setcardpin.interfaces

import android.text.TextWatcher
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISetCardPin {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State>{
        val EVENT_SET_CARD_PIN_SUCCESS: Int
            get() = 1

        var pincode: String
        val clickEvent: SingleClickEvent
        fun handlePressOnNextButton(id: Int)
        fun setCardPin()
    }

    interface State : IBase.State{
        var dialerError: String
        var pincode: String
        var valid: Boolean
        fun getTextWatcher(): TextWatcher
        var sequence: Boolean
        var similar: Boolean
    }
}