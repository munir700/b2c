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
    override var valid: Boolean = true
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

        }

    @get:Bindable
    override var firstName: String = "first name"
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)

        }

    @get:Bindable
    override var lastName: String = "last name"
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)

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

    //

    @get:Bindable
    override var accountIban: String = "1234"
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountIban)

        }

    @get:Bindable
    override var swiftCode: String = "5467"
        set(value) {
            field = value
            notifyPropertyChanged(BR.swiftCode)

        }

    @get:Bindable
    override var countryBankRequirementFieldCode: String = "11344"
        set(value) {
            field = value
            notifyPropertyChanged(BR.countryBankRequirementFieldCode)

        }

}