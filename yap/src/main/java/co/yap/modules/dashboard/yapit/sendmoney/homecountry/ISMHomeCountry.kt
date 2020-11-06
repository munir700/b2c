package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import co.yap.yapcore.IBase

interface ISMHomeCountry {
    interface State: IBase.State {
        var countryCode: String
        var name: String
        var rate: String
        var symbol: String
        var time: String
    }

    interface ViewModel: IBase.ViewModel<State> {

    }

    interface View: IBase.View<ViewModel> {

    }
}