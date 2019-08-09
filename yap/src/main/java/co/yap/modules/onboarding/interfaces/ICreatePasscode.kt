package co.yap.modules.onboarding.interfaces

import android.text.TextWatcher
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface ICreatePasscode {

    interface View : IBase.View<ViewModel>{
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleClickEvent
        fun handlePressOnCreatePasscodeButton(id:Int)
        var mobileNumber:String
    }

    interface State : IBase.State {
        var dialerError: String
        var passcode: String
        var valid: Boolean
        fun getTextWatcher(): TextWatcher
        var sequence: Boolean
        var similar: Boolean
    }
}