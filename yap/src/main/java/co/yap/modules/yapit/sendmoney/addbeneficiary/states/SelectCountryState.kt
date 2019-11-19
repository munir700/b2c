package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.countryutils.country.Country
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.yapcore.BaseState

class SelectCountryState : BaseState(), ISelectCountry.State {

    @Bindable
    override var selectedCountry: Country? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedCountry)

        }
}