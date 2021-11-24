package co.yap.modules.location.kyc_additional_info.birth_info

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.StringUtils

class POBSelectionState : BaseState(), IPOBSelection.State {

    @get:Bindable
    override var cityOfBirth: ObservableField<String> = ObservableField("")
        set(value) {
            field = value
            notifyPropertyChanged(BR.cityOfBirth)
            validate()
        }


    override var selectedCountry: ObservableField<Country?> = ObservableField()
    override var selectedSecondCountry: ObservableField<Country?> = ObservableField()
    override var valid: ObservableField<Boolean> = ObservableField(false)
    override var isDualNational: ObservableBoolean = ObservableBoolean()
    override var eidNationality: ObservableField<String> = ObservableField()
    override var dualNationalityOption: MutableLiveData<Int> = MutableLiveData(0)

    override fun validate() {
        valid.set(
            StringUtils.validateRegix(
                cityOfBirth.get() ?: "",
                "^[a-zA-Z]{1}[a-zA-Z ]{1,50}\$", 2
            ) && selectedCountry.get() != null && (isDualNational.get() == (selectedSecondCountry.get() != null))
        )
    }
}
