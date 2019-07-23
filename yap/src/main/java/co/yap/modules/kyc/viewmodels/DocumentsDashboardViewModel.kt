package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.modules.kyc.states.DocumentsDashboardState
import co.yap.yapcore.BaseViewModel

class DocumentsDashboardViewModel(application: Application) : BaseViewModel<IDocumentsDashboard.State>(application), IDocumentsDashboard.ViewModel {
    override val state: DocumentsDashboardState = DocumentsDashboardState()
}