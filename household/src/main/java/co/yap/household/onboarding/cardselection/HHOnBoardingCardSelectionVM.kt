package co.yap.household.onboarding.cardselection

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.networking.cards.CardsApi
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.requestdtos.SignUpFss
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import javax.inject.Inject

class HHOnBoardingCardSelectionVM @Inject constructor(
    override val state: IHHOnBoardingCardSelection.State,
    private val repository: CardsApi, private val customerRepository: CustomerHHApi
) :
    DaggerBaseViewModel<IHHOnBoardingCardSelection.State>(), IHHOnBoardingCardSelection.ViewModel {
    override val adapter: ObservableField<CardSelectionAdapter>? = ObservableField()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getCardsDesignListRequest(
            mUserLiveData.value?.accountType ?: AccountType.B2C_HOUSEHOLD.name
        ) {
            it?.let {
                state.cardDesigns?.postValue(it)
            }
        }
        if (SessionManager.isExistingUser()) {
            requestGetAddressForPhysicalCard(SessionManager.user?.parentUUID) {
            }
        }
    }

    override fun handleOnClick(id: Int) {
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
            address.designCode = state.designCode?.value
            state.loading = true
            when (val response =
                repository.orderCard(
                    address
                )) {
                is RetroApiResponse.Success -> {
                    trackEventWithAttributes(SessionManager.user, card_color = address.designCode)
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

    override fun requestGetAddressForPhysicalCard(
        uuid: String?,
        apiResponse: ((Boolean) -> Unit?)?
    ) {
        launch {
            when (val response = uuid?.let { repository.getUserAddressRequest(it) }) {
                is RetroApiResponse.Success -> {
                    state.address?.value = response.data.data
                    apiResponse?.invoke(true)
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    apiResponse?.invoke(false)
                }
            }
        }
    }

    override fun signupToFss(request: SignUpFss?, apiResponse: ((Boolean) -> Unit?)?) {
        launch {
            state.loading = true
            when (val response = customerRepository.signupToFss(request)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    apiResponse?.invoke(true)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }

    }
}
