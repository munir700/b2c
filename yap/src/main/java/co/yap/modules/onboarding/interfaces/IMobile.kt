package co.yap.modules.onboarding.interfaces

import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.onboarding.models.UserVerifierProvider
import co.yap.networking.customers.responsedtos.sendmoney.Country
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
        fun createOtp(success: (success : Boolean) -> Unit)
        fun verifyUser(countryCode: String, mobileNumber: String)
        val countriesList:MutableLiveData<ArrayList<Country>?>
        val userVerified: MutableLiveData<String>
         val userVerifier: UserVerifierProvider
    }

    interface State : IBase.State {
        var mobile: String
        var drawbleRight: Drawable?
        var mobileError: String
        var valid: ObservableBoolean
//        var errorVisibility: Int
        var background: Drawable?
        var activeFieldValue: Boolean
        var mobileNoLength: Int
        var countryCode: MutableLiveData<String>
        var mobileNumber: MutableLiveData<String>

    }
}