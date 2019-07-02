package co.yap.modules.onboarding.states

import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.yapcore.BaseState

class MobileState : BaseState(), IMobile.State {
    var PHONE_NUMBER_LENGTH: Int = 13

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
    override var mobile: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile)

        }
        get() {
            return field
        }
    @get:Bindable
    override var mobileError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileError)
        }
        get() {
            return field
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
            notifyChange()
            field = value

        }
        get() {
            return field
        }
}