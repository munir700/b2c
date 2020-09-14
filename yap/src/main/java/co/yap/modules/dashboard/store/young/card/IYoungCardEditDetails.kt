package co.yap.modules.dashboard.store.young.card

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungCardEditDetails {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val adapter: ObservableField<YoungCardEditAdapter>?
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)

    }

    interface State : IBase.State {
        var designCode: MutableLiveData<String>?
        var cardDesigns: MutableLiveData<MutableList<HouseHoldCardsDesign>>?
        var cardName: MutableLiveData<String>

    }
}
