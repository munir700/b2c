package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.yapcore.BaseState

class AddBeneficiaryStates(context: Context) : BaseState(), IAddBeneficiary.State {

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
    override var country: String = "United Arab Emierates"
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
    override var nickName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.nickName)
            validate()
        }

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            validate()
            validateDomesticUser()
        }

    @get:Bindable
    override var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
            validate()
            validateDomesticUser()
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
            notifyPropertyChanged(co.yap.BR.drawbleRight)

        }

    @get:Bindable
    override var mobile: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(co.yap.BR.mobile)
            if (mobile.length < 9) {
                mobileNoLength = 11

            }

        }

    @get:Bindable
    override var mobileNoLength: Int = 11
        set(value) {
            field = value
            notifyPropertyChanged(co.yap.BR.mobileNoLength)
        }

    fun validate() {
        if (!lastName.isNullOrEmpty() && !nickName.isNullOrEmpty() && !lastName.isNullOrEmpty()) {
            valid = true
            notifyPropertyChanged(BR.valid)
        }
    }

    // fields for domestic user


    @get:Bindable
    override var validateDomesticButton: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.validateDomesticButton)
        }

    @get:Bindable
    override var iban: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.iban)
            validateDomesticUser()
        }

    @get:Bindable
    override var confirmIban: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmIban)
            validateDomesticUser()
        }

    fun validateDomesticUser() {
        if (!lastName.isNullOrEmpty() && !iban.isNullOrEmpty() && !lastName.isNullOrEmpty() && !confirmIban.isNullOrEmpty()) {
            validateDomesticButton = true
            notifyPropertyChanged(BR.validateDomesticButton)
        }
    }

}