package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import co.yap.modules.dashboard.more.profile.intefaces.IChangePhoneNumber
import co.yap.modules.dashboard.more.profile.states.ChangePhoneNumberState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.yapcore.SingleClickEvent

class ChangePhoneNumberViewModel(application: Application) :
    MoreBaseViewModel<IChangePhoneNumber.State>(application), IChangePhoneNumber.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override val state: ChangePhoneNumberState = ChangePhoneNumberState(application)

    override fun getCcp(etMobileNumber: EditText) {
        //  etMobileNumber.requestFocus()
        state.etMobileNumber = etMobileNumber
        state.etMobileNumber!!.requestFocus()
        state.etMobileNumber!!.setOnEditorActionListener(onEditorActionListener())
    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                /*if (state.valid){
                            handlePressOnNext()
                        }*/
            }
            false
        }
    }

    override fun onHandlePressOnNextButton() {
        clickEvent.call()
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("Change phone number")
    }
}