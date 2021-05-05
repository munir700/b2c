package co.yap.modules.subaccounts.account.dashboard

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.modules.dashboard.cards.analytics.fragments.CardAnalyticsFragment
import co.yap.modules.subaccounts.account.card.SubAccountCardFragment
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class SubAccountDashBoardVM @Inject constructor(override var state: ISubAccountDashBoard.State) :
    DaggerBaseViewModel<ISubAccountDashBoard.State>(), ISubAccountDashBoard.ViewModel {
    val adapter = ObservableField<SectionsPagerAdapter>()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        adapter.get()?.addFragmentInfo<SubAccountCardFragment>("Cards")
        adapter.get()?.addFragmentInfo<CardAnalyticsFragment>("Analytics")
    }

    override fun handleOnClick(id: Int) {

    }
}