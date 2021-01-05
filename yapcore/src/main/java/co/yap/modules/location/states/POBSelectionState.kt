package co.yap.modules.location.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.countryutils.country.Country
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.StringUtils

class POBSelectionState : BaseState(), IPOBSelection.State {

    @get:Bindable
    override var cityOfBirth: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cityOfBirth)
            validate()
        }


    override var selectedCountry: ObservableField<Country?> = ObservableField()
    override var valid: ObservableField<Boolean> = ObservableField(false)

    private fun validate() {
        valid.set(
            StringUtils.validateRegix(
                cityOfBirth,
                "^[a-zA-Z]{1}[a-zA-Z ]{1,50}\$"
                , 2
            ) && selectedCountry.get() != null
        )
    }
}