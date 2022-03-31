package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.networking.customers.responsedtos.UqudoHeader
import co.yap.networking.customers.responsedtos.UqudoPayLoad
import co.yap.yapcore.BaseState
import com.digitify.identityscanner.BR

class EidInfoReviewState : BaseState(), IEidInfoReview.State {

    override var citizenNumber: MutableLiveData<String> = MutableLiveData()

    @get:Bindable
    override var caption: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.caption)
        }

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            validate()
        }

    @get:Bindable
    override var middleName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.middleName)
        }

    @get:Bindable
    override var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }

    @get:Bindable
    override var nationality: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.nationality)
        }

    @get:Bindable
    override var dateOfBirth: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateOfBirth)
        }

    @get:Bindable
    override var gender: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.gender)
        }

    override var expiryDate: MutableLiveData<String> = MutableLiveData()

    @get:Bindable
    override var fullNameValid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullNameValid)
        }

    @get:Bindable
    override var nationalityValid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.nationalityValid)
        }

    @get:Bindable
    override var genderValid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.genderValid)
        }

    override var expiryDateValid: MutableLiveData<Boolean> = MutableLiveData()

    @get:Bindable
    override var valid: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    override var isShowMiddleName: ObservableBoolean = ObservableBoolean(false)
    override var isShowLastName: ObservableBoolean = ObservableBoolean(false)

    private fun validate() {
        valid = firstName.isNotBlank()
    }

    override var isDateOfBirthValid: ObservableBoolean = ObservableBoolean()
    override var AgeLimit: Int? = 0
    override var isCountryUS: Boolean = false
    override var isTokenValid: ObservableBoolean = ObservableBoolean(true)
    override var uqudoToken: MutableLiveData<String> = MutableLiveData()
    override var payLoadObj: MutableLiveData<UqudoPayLoad> = MutableLiveData()
    override var uqudoHeaderObj: MutableLiveData<UqudoHeader> = MutableLiveData()
    override var isExpired: MutableLiveData<Boolean> = MutableLiveData()
    override var frontImage: MutableLiveData<String> = MutableLiveData()
    override var BackImage: MutableLiveData<String> = MutableLiveData()
    override var showMiddleName: MutableLiveData<Boolean> = MutableLiveData()
}