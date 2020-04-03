package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.extentions.showAlert
import javax.inject.Inject

class SubscriptionVM @Inject constructor(override val state: ISubscription.State) :
    DaggerBaseViewModel<ISubscription.State>()
    , ISubscription.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    override fun handlePressOnClick(context: Context) {
        /*
        * This should be replaced
        * */
        context.showAlert(
            "Are you sure you want to cancel this subscription?",
            "Cancellation will come into effect following the 12-month contract period"
        )

        /*     context.confirm(
                 getString(R.string.location_permission_msg),
                 getString(R.string.location_permission)
             ) {

             }*/
    }
}