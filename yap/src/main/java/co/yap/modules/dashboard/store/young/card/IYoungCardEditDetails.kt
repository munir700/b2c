package co.yap.modules.dashboard.store.young.card

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.IBase

interface IYoungCardEditDetails {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val adapter: ObservableField<YoungCardEditAdapter>?
        fun getCardsDesignListRequest(
            accountType: String,
            apiResponse: ((MutableList<HouseHoldCardsDesign>?) -> Unit?)?
        )
    }

    interface State : IBase.State {
        var address: MutableLiveData<Address>?
        var designCode: MutableLiveData<String>?
        var cardDesigns: MutableLiveData<MutableList<HouseHoldCardsDesign>>?
        var cardName: MutableLiveData<String>
        var childName: MutableLiveData<String>
    }
}

