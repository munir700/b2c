package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.modules.kyc.states.DocumentsDashboardState
import co.yap.yapcore.BaseViewModel
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult

class DocumentsDashboardViewModel(application: Application) : BaseViewModel<IDocumentsDashboard.State>(application), IDocumentsDashboard.ViewModel {
    override val state: DocumentsDashboardState = DocumentsDashboardState()
    override var identity: IdentityScannerResult? = null
    override var name: String = ""
}