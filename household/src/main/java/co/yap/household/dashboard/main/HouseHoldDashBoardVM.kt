package co.yap.household.dashboard.main

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HouseHoldDashBoardVM @Inject constructor(override var state: IHouseholdDashboard.State) :
    DaggerBaseViewModel<IHouseholdDashboard.State>(),
    IHouseholdDashboard.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val adapter = ObservableField<SectionsPagerAdapter>()
    override val profilePictureAdapter = ObservableField<ProfilePictureAdapter>()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}
