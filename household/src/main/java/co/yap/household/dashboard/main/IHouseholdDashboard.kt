package co.yap.household.dashboard.main

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.adpters.SectionsPagerAdapter

interface IHouseholdDashboard {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
        val adapter :ObservableField<SectionsPagerAdapter>
    }

    interface State : IBase.State {
    }
}