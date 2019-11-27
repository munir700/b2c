package co.yap.modules.yapit.sendmoney.home.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        fun handlePressOnBackButton()
        fun handlePressOnAddNow(id: Int)
        fun handlePressOnView(id: Int)
        fun requestDeleteBeneficiary(beneficiaryId: Int)

        var allBeneficiariesList: List<Beneficiary>
        var recentBeneficiariesList: List<Beneficiary>
        fun getState(): LiveData<PagingState>
        var pagingState: MutableLiveData<PagingState>
        val allBeneficiariesLiveData: MutableLiveData<List<Beneficiary>>
        val onDeleteSuccess: MutableLiveData<Int>


    }

    interface View : IBase.View<ViewModel>
}