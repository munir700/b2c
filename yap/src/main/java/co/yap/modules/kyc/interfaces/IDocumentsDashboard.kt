package co.yap.modules.kyc.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import com.digitify.identityscanner.docscanner.models.Identity

interface IDocumentsDashboard {
    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        var identity: Identity?
        var paths: ArrayList<String>
        var name: MutableLiveData<String>
        var allowSkip: MutableLiveData<Boolean>
    }

    interface View : IBase.View<ViewModel>
}