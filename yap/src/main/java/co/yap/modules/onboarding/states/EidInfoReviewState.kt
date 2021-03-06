package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.widgets.State
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
    override var eidExpireLimitDays: MutableLiveData<Int> = MutableLiveData()
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

    override var isDateOfBirthValid: ObservableBoolean = ObservableBoolean(false)
    override var ageLimit: MutableLiveData<Int>? = MutableLiveData()
    override var isCountryUS: Boolean = false
    override var showMiddleName: MutableLiveData<Boolean> = MutableLiveData()
    override var eidImageDownloaded: MutableLiveData<State> = MutableLiveData()
}