package co.yap.modules.dashboard.store.young.subaccounts

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.modules.dashboard.cards.analytics.fragments.CardAnalyticsFragment
import co.yap.modules.subaccounts.account.card.SubAccountCardFragment
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungSubAccountsVM @Inject constructor(override var state: YoungSubAccountState) :
    HiltBaseViewModel<IYoungSubAccounts.State>(), IYoungSubAccounts.ViewModel {
    val adapter = ObservableField<SectionsPagerAdapter>()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        adapter.get()?.addFragmentInfo<SubAccountCardFragment>("Transfers")
        adapter.get()?.addFragmentInfo<CardAnalyticsFragment>("Analytics")
    }

    override fun handleOnClick(id: Int) {
    }
}