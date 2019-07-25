package co.yap.modules.kyc.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.yapcore.BaseState

class KYCHomeState : BaseState(), IKYCHome.State {

    @get:Bindable
    override var valid: Boolean = validate()
        get() = validate()

    @get:Bindable
    override var eidScanStatus: DocScanStatus = DocScanStatus.SCAN_PENDING
        set(value) {
            field = value
            notifyPropertyChanged(BR.eidScanStatus)
            notifyPropertyChanged(BR.valid)
        }

    private fun validate(): Boolean = eidScanStatus != DocScanStatus.SCAN_PENDING
}