package co.yap.modules.kyc.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.modules.kyc.states.DocumentsDashboardState
import co.yap.yapcore.BaseViewModel
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult

class DocumentsDashboardViewModel(application: Application) :
    BaseViewModel<IDocumentsDashboard.State>(application),
    IDocumentsDashboard.ViewModel {

    override val state: DocumentsDashboardState = DocumentsDashboardState()
    override var identity: IdentityScannerResult? = null
    override var name: MutableLiveData<String> = MutableLiveData("")
    override var allowSkip: MutableLiveData<Boolean> = MutableLiveData(false)
}