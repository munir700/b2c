package co.yap.modules.dashboard.more.profile.intefaces

import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.TextView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface IChangePhoneNumber {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun getCcp(etMobileNumber: EditText)
        val clickEvent: SingleClickEvent
        fun onEditorActionListener(): TextView.OnEditorActionListener
        fun onHandlePressOnNextButton()
    }

    interface State : IBase.State {
        var mobile: String
        var drawbleRight: Drawable?
        var background: Drawable?
        var mobileNoLength: Int
        var etMobileNumber: EditText?
        var valid: Boolean
        var errorMessage:String
    }
}