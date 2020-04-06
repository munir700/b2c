package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.toast
import javax.inject.Inject

class SubscriptionVM @Inject constructor(override val state: ISubscription.State) :
    DaggerBaseViewModel<ISubscription.State>()
    , ISubscription.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    override fun handlePressOnClick(context: Context) {
        context.confirm(
            title = "Are you sure you want to cancel this subscription?",
            message = "Cancellation will come into effect following the 12-month contract period",
            positiveButton = "YES",
            negativeButton = "NO",
            cancelable = false
        ) {
            toast(context, "Cancel subscription.")
        }
    }
}