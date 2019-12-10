package co.yap.modules.dashboard.yapit.sendmoney.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.countryutils.country.Country
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class ISendMoney {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIcon: ObservableBoolean
        var leftIcon: ObservableBoolean

        var selectedCountry: ObservableField<Country>
        var transferType: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressButton(id: Int)
    }

    interface View : IBase.View<ViewModel>

}