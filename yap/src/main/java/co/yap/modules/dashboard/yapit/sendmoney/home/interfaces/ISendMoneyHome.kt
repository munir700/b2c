package co.yap.modules.dashboard.yapit.sendmoney.home.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.RecentTransferAdaptor
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiary
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.PagingState

interface ISendMoneyHome {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var clickEvent: SingleClickEvent
        var allBeneficiariesList: List<Beneficiary>
        var recentBeneficiariesList: List<Beneficiary>
        var pagingState: MutableLiveData<PagingState>
        val allBeneficiariesLiveData: MutableLiveData<List<Beneficiary>>
        val onDeleteSuccess: MutableLiveData<Int>
        val recentTransferData: MutableLiveData<List<RecentBeneficiary>>
        val adapter: ObservableField<RecentTransferAdaptor>

        fun handlePressOnBackButton()
        fun handlePressOnAddNow(id: Int)
        fun handlePressOnView(id: Int)
        fun requestDeleteBeneficiary(beneficiaryId: Int)
        fun requestRecentBeneficiaries()
        fun getState(): LiveData<PagingState>


    }

    interface View : IBase.View<ViewModel>
}