package co.yap.sendmoney.home.interfaces

import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.sendmoney.home.adapters.AllBeneficiariesAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISMSearchBeneficiary {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var adapter:AllBeneficiariesAdapter
        fun handlePressOnView(id: Int)
        fun getBeneficiaryFromContact(contact: Contact): Beneficiary
        fun requestDeleteBeneficiary(beneficiaryId: String, completion: () -> Unit)
    }

    interface State : IBase.State
}