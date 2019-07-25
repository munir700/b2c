package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.Utils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EidInfoReviewState : BaseState(), IEidInfoReview.State {
    private var date: Date? = null
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
            val cal = Calendar.getInstance()
            date = Utils.stringToDate(field.replace("/","-"))
            cal.time = date
            val year: Int = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day: Int = cal.get(Calendar.DAY_OF_MONTH)
            field = Utils.getAgeFromDate(year, month, day).toString()

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
            val cal = Calendar.getInstance()
            date = Utils.stringToDate(field.replace("/","-"))
            field = if (cal.time < date) "expiry date is valid" else "expiry date is not valid"
        }

}