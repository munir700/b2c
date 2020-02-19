package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransferConfirmation
import co.yap.yapcore.BaseState

class CashTransferConfirmationState : BaseState(), ICashTransferConfirmation.State {
    override var enteredAmount: ObservableField<String> = ObservableField()
    override var description: ObservableField<CharSequence> =
        ObservableField()
    override var cutOffTimeMsg: ObservableField<String> = ObservableField()
    override var productCode: ObservableField<String> = ObservableField()
    override var transferFee: ObservableField<String> = ObservableField()
    override var referenceNumber: ObservableField<String> = ObservableField()
    override var position: ObservableField<Int> = ObservableField(0)
    override var transferFeeDescription: ObservableField<CharSequence> = ObservableField()
}