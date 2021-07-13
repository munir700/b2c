package co.yap.billpayments.payall.payallsuccess.bottomsheetloder

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayBillLoderBottomSheetState : BaseState(), IPayBillLoderBottomSheet.State {
    override var loadingPercentage: ObservableField<String>? = ObservableField()
}