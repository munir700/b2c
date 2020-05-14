package co.yap.household.dashboard.main

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class HouseHoldDashBoardVM @Inject constructor(override var state: IHouseholdDashboard.State) :
    DaggerBaseViewModel<IHouseholdDashboard.State>(),
    IHouseholdDashboard.ViewModel {

    override val adapter = ObservableField<SectionsPagerAdapter>()
    override val profilePictureAdapter = ObservableField<ProfilePictureAdapter>()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handlePressOnNavigationItem(id: Int) {
        clickEvent.setValue(id)
    }
}