package co.yap.modules.dashboard.store.young.card

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository.getHouseHoldCardsDesign
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AccountType
import javax.inject.Inject

class YoungCardEditDetailsVM @Inject constructor(
    override val state: IYoungCardEditDetails.State
) : DaggerBaseViewModel<IYoungCardEditDetails.State>(), IYoungCardEditDetails.ViewModel {
    override val adapter: ObservableField<YoungCardEditAdapter>? = ObservableField()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getCardsDesignListRequest(
            mUserLiveData.value?.accountType ?: AccountType.B2C_HOUSEHOLD.name
        ) {
            it?.let {
                state.cardDesigns?.postValue(it)
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
                getHouseHoldCardsDesign(accountType = accountType)) {
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
}
