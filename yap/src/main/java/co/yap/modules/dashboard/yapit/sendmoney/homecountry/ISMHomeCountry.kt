package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import co.yap.modules.dashboard.yapit.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISMHomeCountry {
    interface State: IBase.State {
        var countryCode: ObservableField<String>?
        var name: ObservableField<String>?
        var rate: ObservableField<String>?
        var symbol: ObservableField<String>?
        var time: ObservableField<String>?
        var flagDrawableResId: ObservableInt?
        var rightButtonText: ObservableField<String>
    }

    interface ViewModel: IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var recentsAdapter: CoreRecentTransferAdapter
        fun handlePressOnView(id: Int)
    }

    interface View: IBase.View<ViewModel> {

    }
}