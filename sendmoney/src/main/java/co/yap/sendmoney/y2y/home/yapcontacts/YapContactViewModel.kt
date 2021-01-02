package co.yap.sendmoney.y2y.home.yapcontacts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.sendmoney.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState

class YapContactViewModel(application: Application) :
    Y2YBaseViewModel<IYapContact.State>(application),
    IYapContact.ViewModel {

    override val state: IYapContact.State = YapContactState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var contactsAdapter: YapContactsAdaptor = YapContactsAdaptor(mutableListOf())

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}