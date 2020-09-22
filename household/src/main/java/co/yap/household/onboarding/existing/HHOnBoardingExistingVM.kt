package co.yap.household.onboarding.existing

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class HHOnBoardingExistingVM @Inject constructor(
    override val state: IHHOnBoardingExisting.State,
    private val repository: CustomersApi
) : DaggerBaseViewModel<IHHOnBoardingExisting.State>(), IHHOnBoardingExisting.ViewModel {
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
                        MyUserManager.user?.notificationStatuses = it
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
