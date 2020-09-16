package co.yap.modules.dashboard.more.main.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.more.main.interfaces.IMore
import co.yap.modules.dashboard.more.main.states.MoreStates
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class MoreViewModel(application: Application) :
    BaseViewModel<IMore.State>(application),
    IMore.ViewModel {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var BadgeVisibility: Boolean=false
    override val badgeButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var document: GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation? =
        null
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