package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.yapcore.BaseState
import java.util.*

class EidInfoReviewState : BaseState(), IEidInfoReview.State {
    private var date: Date? = null

    @get:Bindable
    override var citizenNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.citizenNumber)
        }

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
    override var middleName: String=""
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

    @get:Bindable
    override var expiryDate: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.expiryDate)
        }

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
    override var dateOfBirthValid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateOfBirthValid)
        }

    @get:Bindable
    override var genderValid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.genderValid)
        }

    @get:Bindable
    override var expiryDateValid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.expiryDateValid)
        }

    @get:Bindable
    override var valid: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

}