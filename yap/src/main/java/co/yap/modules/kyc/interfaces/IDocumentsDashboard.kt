package co.yap.modules.kyc.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult

interface IDocumentsDashboard {
    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        var identity: IdentityScannerResult?
        var name: MutableLiveData<String>
        var allowSkip: MutableLiveData<Boolean>
    }

    interface View : IBase.View<ViewModel>
}