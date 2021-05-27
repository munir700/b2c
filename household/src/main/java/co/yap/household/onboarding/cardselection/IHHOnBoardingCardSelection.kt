package co.yap.household.onboarding.cardselection

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.household.requestdtos.SignUpFss
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.IBase

interface IHHOnBoardingCardSelection {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val adapter: ObservableField<CardSelectionAdapter>?
        fun getCardsDesignListRequest(
            accountType: String,
            apiResponse: ((MutableList<HouseHoldCardsDesign>?) -> Unit?)?
        )

        fun orderHouseHoldPhysicalCardRequest(address: Address, apiResponse: ((Boolean) -> Unit?)?)
        fun requestGetAddressForPhysicalCard(
            uuid: String? = null,
            apiResponse: ((Boolean) -> Unit?)?
        )

        fun signupToFss(request: SignUpFss?, apiResponse: ((Boolean) -> Unit?)?)
    }

    interface State : IBase.State {
        var address: MutableLiveData<Address>?
        var designCode: MutableLiveData<String>?
        var cardDesigns: MutableLiveData<MutableList<HouseHoldCardsDesign>>?
    }
}
