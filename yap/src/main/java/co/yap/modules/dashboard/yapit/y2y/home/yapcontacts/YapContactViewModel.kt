package co.yap.modules.dashboard.yapit.y2y.home.yapcontacts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState

class YapContactViewModel(application: Application) :
    Y2YBaseViewModel<IYapContact.State>(application),
    IYapContact.ViewModel {

    override val state: IYapContact.State = YapContactState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getState(): LiveData<PagingState> {
        val state: MutableLiveData<PagingState> = MutableLiveData()
        state.value = (PagingState.DONE)
        return state
    }


    override fun listIsEmpty(): Boolean {
        return false
    }

    override fun retry() {
    }

}