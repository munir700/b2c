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


}