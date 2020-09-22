package co.yap.household.onboarding.welcome

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHOnBoardingWelcomeVM @Inject constructor(override val state: IHHOnBoardingWelcome.State) :
    DaggerBaseViewModel<IHHOnBoardingWelcome.State>(), IHHOnBoardingWelcome.ViewModel {
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
