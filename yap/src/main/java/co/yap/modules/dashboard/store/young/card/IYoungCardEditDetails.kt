package co.yap.modules.dashboard.store.young.card

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.household.requestdtos.SignUpFss
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungCardEditDetails {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
        fun getCardsDesignListRequest(accountType: String,apiResponse: ((MutableList<HouseHoldCardsDesign>?) -> Unit?)?)
        fun orderHouseHoldPhysicalCardRequest(address: Address, apiResponse: ((Boolean) -> Unit?)?)
    }

    interface State : IBase.State {
        var address: MutableLiveData<Address>?
        var designCode: MutableLiveData<String>?
        var cardDesigns: MutableLiveData<MutableList<HouseHoldCardsDesign>>?
    }
}