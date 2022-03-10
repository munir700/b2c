package co.yap.app.modules.login.states

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.app.R
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.modules.onboarding.models.CountryCode
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.isValidPhoneNumber

class LoginState(application: Application) : BaseState(), ILogin.State {
    var context: Context = application.applicationContext

    @get:Bindable
    override var email: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
            notifyPropertyChanged(BR.valid)
        }

    override var emailError: MutableLiveData<String> = MutableLiveData("")

    override var valid: ObservableBoolean = ObservableBoolean(false)


    fun validate(): Boolean {
        return (email.length > 5 && emailError.value?.isEmpty() ?: false)
    }

    @get:Bindable
    override var twoWayTextWatcher: String = ""
        set(value) {
            field = value

            notifyPropertyChanged(BR.twoWayTextWatcher)
            setTwoWayTextWatcher()
        }


    @get:Bindable
    override var drawbleRight: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawbleRight)
        }
        get() {
            return field
        }

    @get:Bindable
    override var refreshField: Boolean = false
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshField)
        }

    private fun setTwoWayTextWatcher() {
        if ((Utils.isUsernameNumeric(twoWayTextWatcher) && isValidPhoneNumber(
                twoWayTextWatcher,
                "AE"
            )) || Utils.validateEmail(twoWayTextWatcher)
        ) {
            setSuccessUI()
        } else {
            setDefaultUI()
        }
    }

    private fun setDefaultUI() {
        refreshField = true
        valid.set(false)
        drawbleRight = null
    }

    private fun setSuccessUI() {
        refreshField = true
        valid.set(true)
        emailError.value = ""
        drawbleRight = context.resources.getDrawable(R.drawable.path, null)
    }

    override var isError: ObservableBoolean = ObservableBoolean()
    override var isRemember: ObservableBoolean = ObservableBoolean(true)
    override var countryCode: MutableLiveData<String> =
        MutableLiveData(CountryCode.UAE.countryCode ?: "")
    override var mobile: MutableLiveData<String> = MutableLiveData()
    override var mobileNumber: MutableLiveData<String> = MutableLiveData("")

}