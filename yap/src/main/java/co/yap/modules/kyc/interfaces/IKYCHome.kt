package co.yap.modules.kyc.interfaces

import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IKYCHome {
    interface State : IBase.State {
        var valid: Boolean
        var eidScanStatus: DocScanStatus
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnScanCard(id: Int)
        fun handlePressOnNextButton(id: Int)
        fun handlePressOnSkipButton(id: Int)
        fun requestDocuments()
        fun requestDocumentsInformation(success: () -> Unit)
        fun isFromAmendment(): Boolean
        fun navigateTo(fromAmendment: Boolean): Int
    }

    interface View : IBase.View<ViewModel>
}