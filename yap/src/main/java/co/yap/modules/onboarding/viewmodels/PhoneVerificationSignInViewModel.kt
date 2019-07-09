package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.states.PhoneVerificationSignInState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class PhoneVerificationSignInViewModel(application: Application) :
    BaseViewModel<IPhoneVerificationSignIn.State>(application), IPhoneVerificationSignIn.ViewModel {

    override val state: PhoneVerificationSignInState = PhoneVerificationSignInState()
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun handlePressOnSendButton() {
        nextButtonPressEvent.postValue(true)
    }

    override fun handlePressOnResendOTP() {

    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handlePressOnSendButton()
                }
                return false
            }
        }
    }
}