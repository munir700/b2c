package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import android.content.Context
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
    override var nickName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.nickName)

        }

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)

        }

    @get:Bindable
    override var lastName: String = ""
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

}