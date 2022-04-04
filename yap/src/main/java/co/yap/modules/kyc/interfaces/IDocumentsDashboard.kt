package co.yap.modules.kyc.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.uqudo.UqudoScannerManager
import co.yap.networking.customers.responsedtos.UqudoPayLoad
import co.yap.networking.customers.responsedtos.V2DocumentDTO
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.docscanner.models.Identity

interface IDocumentsDashboard {
    interface State : IBase.State {
        var totalProgress: Int
        var currentProgress: Int
        var firstName: ObservableField<String>
        var middleName: ObservableField<String>
        var lastName: ObservableField<String>
        var nationality: ObservableField<String>
        var identityNo: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var identity: Identity?
        var paths: ArrayList<String>

        // Will be used for KYC Amendment
        var amendmentMap: HashMap<String?, List<String>?>?
        var name: MutableLiveData<String>
        var skipFirstScreen: MutableLiveData<Boolean>
        var finishKyc: MutableLiveData<DocumentsResponse>
        var document: GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation?
        fun handlePressOnView(id: Int)
        var clickEvent: SingleClickEvent
        var gotoInformationErrorFragment: MutableLiveData<Boolean>?
        var showProgressBar: MutableLiveData<Boolean>
        var accountStatus: MutableLiveData<String>
        var hideProgressToolbar: MutableLiveData<Boolean>
        var uqudoIdentity: MutableLiveData<V2DocumentDTO>
        var uqudoManager : UqudoScannerManager?
        var comingFrom: MutableLiveData<String>

    }

    interface View : IBase.View<ViewModel>
}
