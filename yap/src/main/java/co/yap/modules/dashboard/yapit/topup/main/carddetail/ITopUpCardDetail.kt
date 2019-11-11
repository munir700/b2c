package co.yap.modules.dashboard.yapit.topup.main.carddetail

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITopUpCardDetail {

    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun onRemoveCard()
        fun handlePressOnView(id: Int)
        fun handlePressOnBackButton(id: Int)
    }

    interface State : IBase.State {
        val cardNickname: ObservableField<String>
        val cardNo: ObservableField<String>
        val cardType: ObservableField<String>
        val cardExpiry: ObservableField<String>
        val title: ObservableField<String>
    }
}