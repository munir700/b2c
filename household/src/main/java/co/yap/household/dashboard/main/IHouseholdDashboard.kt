package co.yap.household.dashboard.main

import androidx.databinding.ObservableField
import co.yap.modules.sidemenu.ProfilePictureAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.adpters.SectionsPagerAdapter

interface IHouseholdDashboard {
    interface View : IBase.View<ViewModel> {}
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        val adapter: ObservableField<SectionsPagerAdapter>
        val profilePictureAdapter: ObservableField<ProfilePictureAdapter>
    }

    interface State : IBase.State {
    }
}
