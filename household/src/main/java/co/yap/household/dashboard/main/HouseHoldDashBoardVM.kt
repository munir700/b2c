package co.yap.household.dashboard.main

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.modules.sidemenu.ProfilePictureAdapter
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.Utils
import javax.inject.Inject

class HouseHoldDashBoardVM @Inject constructor(override var state: IHouseholdDashboard.State) :
    DaggerBaseViewModel<IHouseholdDashboard.State>(),
    IHouseholdDashboard.ViewModel {
    override val adapter = ObservableField<SectionsPagerAdapter>()
    override val profilePictureAdapter = ObservableField<ProfilePictureAdapter>()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}
    override fun handlePressOnView(id: Int) {
        handleOnClick(id)
    }

    override fun handleOnClick(id: Int) {
        when (id) {
            R.id.btnCopyHH -> {
                val info =
                    "Account: ${mUserLiveData.value?.prepaidAccountNo}\nIBAN: ${mUserLiveData.value?.emiratesID}"
                Utils.copyToClipboard(context, info)
                state.toast = "Copied to clipboard"
            }
            else -> clickEvent?.setValue(id)

        }
    }
}
