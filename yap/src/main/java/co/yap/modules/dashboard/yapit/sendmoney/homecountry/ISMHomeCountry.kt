package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase

interface ISMHomeCountry {
    interface State: IBase.State {
        var countryCode: ObservableField<String>?
        var name: ObservableField<String>?
        var rate: ObservableField<String>?
        var symbol: ObservableField<String>?
        var time: ObservableField<String>?
    }

    interface ViewModel: IBase.ViewModel<State> {

    }

    interface View: IBase.View<ViewModel> {

    }
}