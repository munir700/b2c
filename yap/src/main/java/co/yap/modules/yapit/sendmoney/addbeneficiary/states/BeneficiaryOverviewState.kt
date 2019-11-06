package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryOverview
import co.yap.yapcore.BaseState

class BeneficiaryOverviewState : BaseState(), IBeneficiaryOverview.State {

    @get:Bindable
    override var flagDrawableResId: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.flagDrawableResId)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }


    @get:Bindable
    override var country: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.country)

        }


    @get:Bindable
    override var transferType: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferType)

        }


    @get:Bindable
    override var currency: String = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)

        }

    @get:Bindable
    override var nickName: String = "nick name"
        set(value) {
            field = value
            notifyPropertyChanged(BR.nickName)
            validate()
        }

    @get:Bindable
    override var firstName: String = "first name"
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            validate()
        }

    @get:Bindable
    override var lastName: String = "last name"
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
            validate()
        }


    @get:Bindable
    override var phoneNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumber)
        }

// for phone number field validation to be work on

    @get:Bindable
    override var drawbleRight: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawbleRight)

        }

    @get:Bindable
    override var mobile: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile)
            if (mobile.length < 9) {
                mobileNoLength = 11

            }

        }

    @get:Bindable
    override var mobileNoLength: Int = 11
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileNoLength)
        }

    fun validate() {
        if (!lastName.isNullOrEmpty() && !nickName.isNullOrEmpty() && !lastName.isNullOrEmpty()) {
            valid = true
            notifyPropertyChanged(BR.valid)
        }
    }
}