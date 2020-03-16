package co.yap.sendMoney.addbeneficiary.states

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import co.yap.countryutils.country.Country
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.translation.Strings.screen_add_beneficiary_display_text_select_country
import co.yap.translation.Translator
import co.yap.yapcore.BaseState

class SelectCountryState(val application: Application) : BaseState(), ISelectCountry.State {

    @Bindable
    override var selectedCountry: Country? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedCountry)
            valid = field!=null && !field!!.getName().equals(Translator.getString(application.applicationContext,screen_add_beneficiary_display_text_select_country))
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }
}