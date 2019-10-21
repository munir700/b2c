package co.yap.modules.kyc.interfaces

import co.yap.yapcore.IBase
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult

interface IDocumentsDashboard {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State> {
        var identity: IdentityScannerResult?
        var name: String
    }
    interface View : IBase.View<ViewModel>
}