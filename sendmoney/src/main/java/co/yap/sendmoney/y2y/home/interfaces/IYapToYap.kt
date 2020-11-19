package co.yap.sendmoney.y2y.home.interfaces

import androidx.databinding.ObservableBoolean
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapToYap {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var recentsAdapter: CoreRecentTransferAdapter
        fun handlePressOnView(id: Int)
        fun getRecentBeneficiaries()

    }

    interface State : IBase.State {
        var isRecentsVisible: ObservableBoolean
        var isNoRecents:ObservableBoolean

    }
}