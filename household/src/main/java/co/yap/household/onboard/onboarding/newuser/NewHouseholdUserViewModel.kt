package co.yap.household.onboard.onboarding.newuser

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.household.setpin.setnewpin.SetPinDataModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class NewHouseholdUserViewModel @Inject constructor(override var state: INewHouseHoldUser.State) :
    DaggerBaseViewModel<INewHouseHoldUser.State>(), INewHouseHoldUser.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let {
            var accountInfo:AccountInfo = it.getParcelable(OnBoardingHouseHoldActivity.USER_INFO)
            accountInfo.let { accInfo ->
                state.accountInfo.value = accInfo
                state.firstName.value = accInfo.currentCustomer.firstName
                state.lastName.value = accInfo.currentCustomer.lastName
            }
        }
    }

    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}