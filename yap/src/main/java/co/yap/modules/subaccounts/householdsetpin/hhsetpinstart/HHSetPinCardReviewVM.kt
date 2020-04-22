package co.yap.modules.subaccounts.householdsetpin.hhsetpinstart

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHSetPinCardReviewVM  @Inject constructor(override val state: IHHSetPinCardReview.State) :
    DaggerBaseViewModel<IHHSetPinCardReview.State>(), IHHSetPinCardReview.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }
}