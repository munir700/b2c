package co.yap.billpayments.billerdetail

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.enums.BillCategory

interface IBillerDetail {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun getScreenTitle(billCategory: BillCategory?): String
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
    }
}
