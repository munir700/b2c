package co.yap.household.onboarding.welcome

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHOnBoardingWelcomeVM @Inject constructor(override val state: HHOnBoardingWelcomeState) :
    HiltBaseViewModel<IHHOnBoardingWelcome.State>(), IHHOnBoardingWelcome.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let {
            val accountInfo: AccountInfo? =
                it.getParcelable<AccountInfo>(AccountInfo::class.java.simpleName)
        }
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            val accountInfo: AccountInfo? =
                it.getParcelable<AccountInfo>(AccountInfo::class.java.simpleName)

        }
    }

    override fun handleOnClick(id: Int) {
    }
}
