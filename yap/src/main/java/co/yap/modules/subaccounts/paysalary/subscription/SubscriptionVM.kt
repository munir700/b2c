package co.yap.modules.subaccounts.paysalary.subscription

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class SubscriptionVM @Inject constructor(override val state: ISubscription.State) : DaggerBaseViewModel<ISubscription.State>() {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        TODO("Not yet implemented")
    }
}