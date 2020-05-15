package co.yap.household.dashboard.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseState


class HouseholdHomeState : BaseState(), IHouseholdHome.State {
    override val transactionList: ObservableField<MutableList<HomeTransactionListData>> =
        ObservableField(mutableListOf())
    override var progress: MutableLiveData<Int> = MutableLiveData(30)
}
