package co.yap.modules.dashboard.more.main.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.more.main.interfaces.IMore
import co.yap.modules.dashboard.more.main.states.MoreStates
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.getFormattedDate
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.MyUserManager
import kotlinx.coroutines.delay

class MoreViewModel(application: Application) :
    MoreBaseViewModel<IMore.State>(application),
    IMore.ViewModel ,IRepositoryHolder<CustomersRepository>{

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var BadgeVisibility: Boolean=false
    override val badgeButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var document: GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation? = null
    override val repository: CustomersRepository = CustomersRepository
    override val state: MoreStates = MoreStates()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }

    override fun handlePressOnBadge() {
        badgeButtonPressEvent.value= true

    }

    override fun requestProfileDocumentsInformation(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.getMoreDocumentsByType("EMIRATES_ID")) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.document =
                        response.data.data?.customerDocuments?.get(0)?.documentInformation

                    val data = response.data
                    data.data?.dateExpiry?.let {
                        trackEvent(KYCEvents.EID_EXPIRE_DATE.type)
                        trackEventWithAttributes(
                            MyUserManager.user,
                            eidExpireDate = getFormattedDate(it)
                        ) }
                    launch {
                     delay(1000)
                        success()
                        state.loading = false
                    }
                    }

                is RetroApiResponse.Error -> {
                    if (response.error.statusCode == 400 || response.error.actualCode == "1073")
                        MyUserManager.eidStatus =
                            EIDStatus.NOT_SET  //set the document is required if not found
                    state.loading = false
                }
            }
        }
    }
}