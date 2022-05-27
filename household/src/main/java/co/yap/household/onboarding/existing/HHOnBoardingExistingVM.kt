package co.yap.household.onboarding.existing

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHOnBoardingExistingVM @Inject constructor(
    override val state: HHOnBoardingExistingState,
    private val repository: CustomersApi
) : HiltBaseViewModel<IHHOnBoardingExisting.State>(), IHHOnBoardingExisting.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun subAccountInvitationStatus(
        notificationStatus: String,
        apiResponse: ((String?) -> Unit?)?
    ) {
        launch {
            state.loading = true
            when (val response = repository.getSubAccountInviteStatus(notificationStatus)) {
                is RetroApiResponse.Success -> {

                    response.data.data?.let {
                        SessionManager.user?.notificationStatuses = it
                        apiResponse?.invoke(it)
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(null)
                    state.loading = false
                }
            }
        }
    }

    override fun handleOnClick(id: Int) {
    }
}
