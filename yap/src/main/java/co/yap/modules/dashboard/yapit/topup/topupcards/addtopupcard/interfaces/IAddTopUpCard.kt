package co.yap.modules.dashboard.yapit.topup.topupcards.addtopupcard.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IAddTopUpCard {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun addTopUpCard(sessionId: String, alias: String, color: String)
        val isCardAdded: MutableLiveData<Boolean>
    }

    interface State : IBase.State
}