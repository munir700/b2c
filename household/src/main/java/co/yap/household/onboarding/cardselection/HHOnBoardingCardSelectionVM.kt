package co.yap.household.onboarding.cardselection

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.networking.cards.CardsApi
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class HHOnBoardingCardSelectionVM @Inject constructor(
    override val state: IHHOnBoardingCardSelection.State,
    private val repository: CardsApi
) :
    DaggerBaseViewModel<IHHOnBoardingCardSelection.State>(), IHHOnBoardingCardSelection.ViewModel {
    override val clickEvent = SingleClickEvent()
    override val adapter: ObservableField<CardSelectionAdapter>? = ObservableField()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getCardsDesignListRequest(
            mUserLiveData.value?.accountType ?: AccountType.B2C_HOUSEHOLD.name
        ) {
            it?.let {
                state.cardDesigns?.postValue(it)
            }
        }
    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getCardsDesignListRequest(
        accountType: String,
        apiResponse: ((MutableList<HouseHoldCardsDesign>?) -> Unit?)?
    ) {
        launch {
            state.loading = true
            when (val response =
                repository.getHouseHoldCardsDesign(accountType = accountType)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNullOrEmpty()) return@launch
                    response.data.data?.let {
                        apiResponse?.invoke(it)
                        adapter?.get()?.setData(it)
                    }
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    apiResponse?.invoke(null)
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun orderHouseHoldPhysicalCardRequest(
        address: Address,
        apiResponse: ((Boolean) -> Unit?)?
    ) {
        launch {
            state.loading = true
            when (val response =
                repository.orderCard(
                    address
                )) {
                is RetroApiResponse.Success -> {
                    trackEventWithAttributes(MyUserManager.user, card_color = address.designCode)
                    apiResponse?.invoke(true)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(false)
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }
}
