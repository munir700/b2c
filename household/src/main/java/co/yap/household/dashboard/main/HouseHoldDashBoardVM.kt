package co.yap.household.dashboard.main

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.modules.sidemenu.ProfilePictureAdapter
import co.yap.networking.messages.MessagesRepository.getHelpDeskContact
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.extentions.share
import co.yap.yapcore.managers.SessionManager
import javax.inject.Inject

class HouseHoldDashBoardVM @Inject constructor(override var state: IHouseholdDashboard.State) :
    DaggerBaseViewModel<IHouseholdDashboard.State>(),
    IHouseholdDashboard.ViewModel {
    override val adapter = ObservableField<SectionsPagerAdapter>()
    override val profilePictureAdapter = ObservableField<ProfilePictureAdapter>()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getHelpPhoneNo()
    }

    override fun handlePressOnView(id: Int) {
        handleOnClick(id)
    }

    override fun handleOnClick(id: Int) {
        when (id) {
            R.id.btnCopyHH -> {
                val info =
                    "Account: ${mUserLiveData.value?.prepaidAccountNo}\nIBAN: ${mUserLiveData.value?.emiratesID}"
                context.share(text = info, title = "Share")
            }
            else -> clickEvent?.setValue(id)

        }
    }

    private fun getHelpPhoneNo() {
        launch {
            when (val response =
                getHelpDeskContact()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        SessionManager.helpPhoneNumber = it
                    }
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }
}
