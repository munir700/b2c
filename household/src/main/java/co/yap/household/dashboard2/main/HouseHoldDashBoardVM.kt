package co.yap.household.dashboard2.main

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HouseHoldDashBoardVM @Inject constructor(override var state: IHouseholdDashboard.State) :
    DaggerBaseViewModel<IHouseholdDashboard.State>(),
    IHouseholdDashboard.ViewModel {

    override var adapter: ObservableField<SectionsPagerAdapter>? = null


    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }
}