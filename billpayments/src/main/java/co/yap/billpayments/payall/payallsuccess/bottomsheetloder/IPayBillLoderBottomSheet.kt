package co.yap.billpayments.payall.payallsuccess.bottomsheetloder

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase

interface IPayBillLoderBottomSheet {
    interface State : IBase.State {
        var loadingPercentage: ObservableField<String>?
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel> {
    }
}