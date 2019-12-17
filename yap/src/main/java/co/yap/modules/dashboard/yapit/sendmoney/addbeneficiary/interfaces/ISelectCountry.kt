package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISelectCountry {

    interface State : IBase.State {
        var selectedCountry: Country?
        var valid: Boolean
        var isDomestic: ObservableField<Boolean>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnSeclectCountry(id: Int)
        fun onTransparentViewClick(id: Int)
        var countries: ArrayList<Country>
        val populateSpinnerData: MutableLiveData<List<Country>>
        fun onCountrySelected(pos: Int)

    }

    interface View : IBase.View<ViewModel>
}