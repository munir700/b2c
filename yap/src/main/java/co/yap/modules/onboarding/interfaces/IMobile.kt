package co.yap.modules.onboarding.interfaces

import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IMobile {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnNext()
        fun getCcp(etMobileNumber: EditText)
        fun onEditorActionListener(): TextView.OnEditorActionListener
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var mobile: String
        var drawbleRight: Drawable?
        var mobileError: String
        var valid: ObservableBoolean
        var errorVisibility: Int
        var background: Drawable?
        var activeFieldValue: Boolean
        var mobileNoLength: Int

        var isError: ObservableBoolean
        var countryCode: ObservableField<String>
        var mobileNumber: MutableLiveData<String>

    }
}