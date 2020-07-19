package co.yap.household.onboard.onboarding.existinghousehold

import android.app.Application
import android.util.Log
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.models.ApiError
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.managers.MyUserManager
import io.reactivex.Single
@Deprecated("")
class ExistingHouseholdViewModel(application: Application) :
    BaseViewModel<IExistingHouseHold.State>(application = application), IExistingHouseHold.ViewModel{
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var subAccountInvitationStatus: SingleLiveEvent<String> = SingleLiveEvent()
    private val customersRepository: CustomersRepository = CustomersRepository

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun setUserData(accountInfo: AccountInfo){
        state.firstName = accountInfo.currentCustomer.firstName
        state.lastName = accountInfo.currentCustomer.lastName
    }

    override fun subAccountInvitationStatus(notificationStatus: String) {
        launch {
            state.loading = true
            when (val response =
                notificationStatus?.let {
                    customersRepository.getSubAccountInviteStatus(
                        it
                    )
                }) {
                is RetroApiResponse.Success -> {

                    response.data.data?.let {
                        MyUserManager.user?.notificationStatuses = it
                        subAccountInvitationStatus.postValue(it)
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
//                    response.data.data?.let { subAccountInvitationStatus.postValue(it) }
                    state.loading = false
//                    handleAttemptsError(response.error)
                }
            }
        }
    }

    override val state = ExistingHouseHoldState(application)


}