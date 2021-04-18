package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IWaitingList {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnNext(id: Int)
        fun requestWaitingRanking()
        fun stopRankingMsgRequest()
    }

    interface State : IBase.State {
        var waitingBehind: ObservableField<String>?
        var rank: ObservableField<String>?
        var jump: ObservableField<String>?
    }
}