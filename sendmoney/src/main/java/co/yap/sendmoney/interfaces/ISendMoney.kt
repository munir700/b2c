package co.yap.sendmoney.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISendMoney {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIcon: ObservableBoolean
        var leftIcon: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var selectedCountry: MutableLiveData<Country>
        var beneficiary: MutableLiveData<Beneficiary>
        var otpSuccess:MutableLiveData<Boolean>
        var countriesList: List<Country>?
        var selectedResidenceCountry: Country?
    }

    interface View : IBase.View<ViewModel>

}