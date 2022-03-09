package co.yap.app.modules.login.interfaces

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ILogin {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var isAccountBlocked: MutableLiveData<Boolean>
        fun onEditorActionListener(): TextView.OnEditorActionListener
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun saveUserDetails(
            mobile: String?, countryCode: String?,
            isRemember: Boolean?
        )
    }

    interface State : IBase.State {
        var email: String
        var emailError: MutableLiveData<String>
        var valid: ObservableBoolean
        var twoWayTextWatcher: String
        var drawbleRight: Drawable?
        var refreshField: Boolean
        var isError: ObservableBoolean
        var countryCode: ObservableField<String>
        var mobile: ObservableField<String>
        var mobileNumber: MutableLiveData<String>
    }
}