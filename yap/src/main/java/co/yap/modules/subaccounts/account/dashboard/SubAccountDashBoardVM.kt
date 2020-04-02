package co.yap.modules.subaccounts.account.dashboard

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class SubAccountDashBoardVM @Inject constructor(override var state: ISubAccountDashBoard.State) :
    DaggerBaseViewModel<ISubAccountDashBoard.State>(), ISubAccountDashBoard.ViewModel {
    val adapter = ObservableField<SectionsPagerAdapter>()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

}