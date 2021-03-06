package co.yap.modules.kyc.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.modules.kyc.states.DocumentsDashboardState
import co.yap.modules.kyc.uqudo.UqudoScannerManager
import co.yap.networking.customers.responsedtos.V2DocumentDTO
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.SessionManager

class DocumentsDashboardViewModel(application: Application) :
    BaseViewModel<IDocumentsDashboard.State>(application),
    IDocumentsDashboard.ViewModel {

    override val state: DocumentsDashboardState = DocumentsDashboardState()
    override var amendmentMap: HashMap<String?, List<String>?>? = null
    override var name: MutableLiveData<String> = MutableLiveData("")
    override var skipFirstScreen: MutableLiveData<Boolean> = MutableLiveData(false)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var gotoInformationErrorFragment: MutableLiveData<Boolean>? = MutableLiveData(false)
    override var showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    override var finishKyc: MutableLiveData<DocumentsResponse> = MutableLiveData()
    override var accountStatus: MutableLiveData<String> = MutableLiveData()
    override var comingFrom: MutableLiveData<String> = MutableLiveData()

    override var document: GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation? =
        null
    override var hideProgressToolbar = MutableLiveData(false)
    override var uqudoIdentity: MutableLiveData<V2DocumentDTO> = MutableLiveData()
    override var uqudoManager: UqudoScannerManager? = null

    override fun onCreate() {
        super.onCreate()
        accountStatus.value = SessionManager.user?.notificationStatuses
        state.middleName.set(
            SharedPreferenceManager.getInstance(context)
                .getValueString(Constants.KYC_MIDDLE_NAME)
        )
        state.firstName.set(
            SharedPreferenceManager.getInstance(context)
                .getValueString(Constants.KYC_FIRST_NAME)
        )
        state.lastName.set(
            SharedPreferenceManager.getInstance(context)
                .getValueString(Constants.KYC_LAST_NAME)
        )
        state.nationality.set(SessionManager.user?.currentCustomer?.nationality)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}
