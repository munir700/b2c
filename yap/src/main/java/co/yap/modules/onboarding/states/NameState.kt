package co.yap.modules.onboarding.states

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IName
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.StringUtils

class NameState(application: Application) : BaseState(), IName.State {

    override var dummyStrings: Array<String> = arrayOf("0123")

    val context: Context = application.applicationContext

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            notifyPropertyChanged(BR.valid)
            setFirstNameTextWatcher(firstName)
        }

    @get:Bindable
    override var firstNameError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstNameError)
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
            notifyPropertyChanged(BR.valid)
            setLastNameTextWatcher(lastName)
        }

    @get:Bindable
    override var lastNameError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastNameError)
            notifyPropertyChanged(BR.valid)
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
    override var drawbleRightLastName: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawbleRightLastName)

        }
        get() {
            return field
        }

    @get:Bindable
    override var valid: Boolean = false
        get() = validate()

    private fun validate(): Boolean {

        return StringUtils.validateName(firstName) && StringUtils.validateName(lastName) && firstNameError.isNullOrEmpty() && lastNameError.isNullOrEmpty()
    }


    fun setFirstNameTextWatcher(value: String) {

        if (!value.isNullOrEmpty() && value.length >= 3) {

            if (StringUtils.validateName(value)) {
                firstNameError = ""
                notifyPropertyChanged(BR.firstNameError)
                drawbleRight = context!!.resources.getDrawable(co.yap.yapcore.R.drawable.path)


            } else {
                valid = false
                lastNameError = "error"
                firstNameError = ""
                drawbleRight = null


            }
        } else {
            drawbleRight = null

        }
    }

    fun setLastNameTextWatcher(value: String) {

        if (!value.isNullOrEmpty() && value.length >= 3) {

            if (StringUtils.validateName(value)) {
                valid = true
                lastNameError = ""
                notifyPropertyChanged(BR.lastNameError)
                drawbleRightLastName = context!!.resources.getDrawable(co.yap.yapcore.R.drawable.path)

            } else {
                valid = false
                lastNameError = "error"
                notifyPropertyChanged(BR.lastNameError)
                drawbleRightLastName = null

            }
        } else {
            drawbleRightLastName = null

        }
    }
}