package co.yap.household.onboarding.kycsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import javax.inject.Inject

class KycSuccessVM @Inject constructor(override var state :KycSuccessState) :
    HiltBaseViewModel<IKycSuccess.State>(), IKycSuccess.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.address?.value = extras.getParcelable(Constants.ADDRESS)
        }
    }

    override fun handleOnClick(id: Int) {
    }
}