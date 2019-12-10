package co.yap.modules.dashboard.yapit.sendmoney.home.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.home.adapters.RecentTransferAdaptor
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.PagingState

interface ISendMoneyHome {
    interface State : IBase.State {
        var isNoBeneficiary: ObservableField<Boolean>
        var hasBeneficiary: ObservableField<Boolean>
        var isSearching: ObservableField<Boolean>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var allBeneficiariesList: List<Beneficiary>
        var recentBeneficiariesList: List<Beneficiary>
        var pagingState: MutableLiveData<PagingState>
        val allBeneficiariesLiveData: MutableLiveData<List<Beneficiary>>
        val onDeleteSuccess: MutableLiveData<Int>
        val recentTransferData: MutableLiveData<List<Beneficiary>>
        val adapter: ObservableField<RecentTransferAdaptor>

        fun handlePressOnView(id: Int)
        fun requestDeleteBeneficiary(beneficiaryId: Int)
        fun requestRecentBeneficiaries()
        fun getState(): LiveData<PagingState>
        val searchQuery: MutableLiveData<String>
        val isSearching: MutableLiveData<Boolean>



    }

    interface View : IBase.View<ViewModel>
}