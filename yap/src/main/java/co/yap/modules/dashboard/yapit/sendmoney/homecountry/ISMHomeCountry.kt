package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.countryutils.country.Country
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISMHomeCountry {
    interface State : IBase.State {
        var countryCode: ObservableField<String>?
        var name: ObservableField<String>?
        var rate: ObservableField<String>?
        var symbol: ObservableField<String>?
        var time: ObservableField<String>?
        var rightButtonText: ObservableField<String>
        var isNoRecentsBeneficiries: ObservableBoolean
        var isRecentsVisible: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var recentsAdapter: CoreRecentTransferAdapter
        var benefitsAdapter: SMHomeCountryBenefitsAdapter
        var homeCountry:Country?
        var benefitsList: ArrayList<String>
        fun handlePressOnView(id: Int)
    }

    interface View : IBase.View<ViewModel> {

    }
}