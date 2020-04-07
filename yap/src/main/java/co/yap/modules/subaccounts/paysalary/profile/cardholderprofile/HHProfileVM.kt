package co.yap.modules.subaccounts.paysalary.profile.cardholderprofile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHProfileVM @Inject constructor(override val state: IHHProfile.State) :
    DaggerBaseViewModel<IHHProfile.State>() {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }
}