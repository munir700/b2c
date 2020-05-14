package co.yap.household.dashboard.main

import androidx.databinding.ObservableField
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.adpters.SectionsPagerAdapter

interface IHouseholdDashboard {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
        val adapter :ObservableField<SectionsPagerAdapter>
        val profilePictureAdapter:ObservableField<ProfilePictureAdapter>
        val clickEvent: SingleClickEvent
        fun handlePressOnNavigationItem(id: Int)
    }

    interface State : IBase.State {
    }
}