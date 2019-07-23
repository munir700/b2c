package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.yapcore.BaseState

class EidInfoReviewState : BaseState(), IEidInfoReview.State {
    @get:Bindable
    override var fullName: String = "Nada Hassan"
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)
        }
    @get:Bindable
    override var nationality: String = "Canadian"
        set(value) {
            field = value
            notifyPropertyChanged(BR.nationality)
        }
    @get:Bindable
    override var dateOfBirth: String = "01/01/1979"
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateOfBirth)
        }
    @get:Bindable
    override var gender: String = "Female"
        set(value) {
            field = value
            notifyPropertyChanged(BR.gender)
        }
    @get:Bindable
    override var expiryDate: String = "01/01/2019"
        set(value) {
            field = value
            notifyPropertyChanged(BR.expiryDate)
        }
}