package co.yap.billpayments.payall.payallsuccess.bottomsheetloder

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayBillLoderBottomSheetState : BaseState(), IPayBillLoderBottomSheet.State {
    override var loadingPercentage: ObservableField<Int> = ObservableField(0)
    override var isResponceReceived: ObservableBoolean = ObservableBoolean(false)
}