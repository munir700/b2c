package co.yap.modules.kyc.interfaces

import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult

interface IKYCHome {
    interface State : IBase.State {
        var valid: Boolean
        var eidScanStatus: DocScanStatus
        val name: Array<String?>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnScanCard(id: Int)
        fun handlePressOnNextButton(id: Int)
        fun handlePressOnSkipButton(id: Int)
        fun onEIDScanningComplete(result: IdentityScannerResult)
    }

    interface View : IBase.View<ViewModel>
}