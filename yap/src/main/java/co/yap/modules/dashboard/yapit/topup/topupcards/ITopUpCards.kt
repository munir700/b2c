package co.yap.modules.dashboard.yapit.topup.topupcards

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITopUpCards {

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
        var rightButtonVisibility: Int
        var leftButtonVisibility: Int
        val valid :ObservableField<Boolean>
        val enableAddCard :ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton(id: Int)
        fun handlePressOnView(id: Int)
        fun getPaymentCards()
        val clickEvent: SingleClickEvent
        val topUpCards: MutableLiveData<List<TopUpCard>>
    }

    interface View : IBase.View<ViewModel>
}