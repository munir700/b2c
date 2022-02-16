package co.yap.app.modules.login.interfaces

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface ILogin {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val signInButtonPressEvent: SingleLiveEvent<Boolean>
        val signUpButtonPressEvent: SingleLiveEvent<Boolean>
        var isAccountBlocked:MutableLiveData<Boolean>
        fun handlePressOnSignUp()
        fun onEditorActionListener(): TextView.OnEditorActionListener
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var email: String
        var emailError: MutableLiveData<String>
        var valid: Boolean
        var twoWayTextWatcher: String
        var drawbleRight: Drawable?
        var refreshField: Boolean
    }
}