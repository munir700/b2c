package co.yap.app.modules.login.interfaces

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Country
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ILogin {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var isAccountBlocked: MutableLiveData<Boolean>
        fun onEditorActionListener(): TextView.OnEditorActionListener
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        val countriesList: MutableLiveData<ArrayList<Country>>
        val countries: ArrayList<Country>
        fun setFeatureFlagCall()
    }

    interface State : IBase.State {
        var email: String
        var emailError: MutableLiveData<String>
        var valid: ObservableBoolean
        var twoWayTextWatcher: String
        var drawbleRight: Drawable?
        var refreshField: Boolean
        var isError: ObservableBoolean
        var isRemember: ObservableBoolean
        var countryCode: MutableLiveData<String>
        var mobile: MutableLiveData<String>
        var mobileNumber: MutableLiveData<String>
    }
}