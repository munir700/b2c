package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.modules.onboarding.interfaces.IEidInfoReviewAmendment
import co.yap.widgets.State
import co.yap.yapcore.BaseState
import com.digitify.identityscanner.BR
import java.util.*

class EidInfoReviewAmendmentState : BaseState(), IEidInfoReviewAmendment.State {

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

    override var dateOfBirth: MutableLiveData<String> = MutableLiveData("")

    @get:Bindable
    override var gender: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.gender)
        }

    @get:Bindable
    override var expiryDate: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.expiryDate)
        }
    override var eidExpireLimitDays: MutableLiveData<Int> = MutableLiveData()
    @get:Bindable
    override var fullNameValid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullNameValid)
        }

    @get:Bindable
    override var citizenNumberValid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.citizenNumberValid)
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
    override var dobCalendar: Calendar = Calendar.getInstance()
    override var expiryCalendar: Calendar = Calendar.getInstance()
    override var nationality: MutableLiveData<Country?> = MutableLiveData(null)

    @get:Bindable
    override var previousFirstName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.previousFirstName)
        }

    @get:Bindable
    override var previousMiddleName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.previousMiddleName)
        }

    @get:Bindable
    override var previousLastName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.previousLastName)
        }

    @get:Bindable
    override var previousNationality: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.previousNationality)
        }

    @get:Bindable
    override var previousDateOfBirth: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.previousDateOfBirth)
        }

    @get:Bindable
    override var previousGender: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.previousGender)
        }

    @get:Bindable
    override var previousExpiryDate: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.previousExpiryDate)
        }

    @get:Bindable
    override var previousCitizenNumber: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.previousCitizenNumber)
        }

    override var isDateOfBirthValid: ObservableBoolean = ObservableBoolean()
    override var ageLimit: Int? = 0
    override var isCountryUS: Boolean = false
    override var countryName: ObservableField<String> = ObservableField()
    override var errorScreenVisited: Boolean = false
    override var eidImageDownloaded: MutableLiveData<State> = MutableLiveData()
}