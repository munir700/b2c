package co.yap.modules.location.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.countryutils.country.Country
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState

class POBSelectionState : BaseState(), IPOBSelection.State {

    @get:Bindable
    override var cityOfBirth: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cityOfBirth)
            validate()
        }

    @Bindable
    override var selectedCountry: Country? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedCountry)
            validate()
        }

    override var valid: ObservableField<Boolean> = ObservableField(false)

    fun validate() {
        valid.set(
            cityOfBirth.isNotBlank() && !selectedCountry?.getName().equals("Select country")
        )
    }

}