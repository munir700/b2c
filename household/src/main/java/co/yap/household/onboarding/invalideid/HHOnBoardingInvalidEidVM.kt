package co.yap.household.onboarding.invalideid

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.messages.MessagesApi
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHOnBoardingInvalidEidVM @Inject constructor(
    override val state: IHHOnBoardingInvalidEid.State,
    private val repository: MessagesApi
) :
    DaggerBaseViewModel<IHHOnBoardingInvalidEid.State>(), IHHOnBoardingInvalidEid.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getHelpDeskPhone()
    }

    override fun getHelpDeskPhone() {
        launch {
            state.loading = true
            when (val response =
                repository.getHelpDeskContact()) {
                is RetroApiResponse.Error -> {
                    state.loading = false
                }
                is RetroApiResponse.Success -> {
                    state.loading = false
                    response.data.data?.let { state.contactPhone.value = it }
                }
            }
        }
    }

    override fun handleOnClick(id: Int) {
    }
}
