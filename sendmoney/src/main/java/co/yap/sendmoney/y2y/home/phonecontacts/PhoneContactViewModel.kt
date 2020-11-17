package co.yap.sendmoney.y2y.home.phonecontacts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.sendmoney.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.extentions.getLocalContacts
import co.yap.yapcore.helpers.extentions.removeOwnContact
import kotlin.math.ceil

class PhoneContactViewModel(application: Application) :
    Y2YBaseViewModel<IPhoneContact.State>(application),
    IPhoneContact.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IPhoneContact.State = PhoneContactState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    var pagingState: MutableLiveData<PagingState> = MutableLiveData()
    override var phoneContactLiveData: MutableLiveData<List<Contact>> = MutableLiveData()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getState(): LiveData<PagingState> {
        return pagingState
    }

    override fun listIsEmpty(): Boolean {
        return phoneContactLiveData.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        viewModelBGScope.close()
        super.onCleared()
    }

    override fun getY2YBeneficiaries() {
        pagingState.value = PagingState.LOADING
        if (listIsEmpty()) {
            getLocalContactsFromServer()
        } else {
            phoneContactLiveData.postValue(phoneContactLiveData.value)
            pagingState.postValue(PagingState.DONE)
        }
    }

    private fun getLocalContactsFromServer() {
        launch(Dispatcher.LongOperation) {
            val localContacts = getLocalContacts(context).removeOwnContact()
            if (localContacts.isEmpty()) {
                phoneContactLiveData.value = mutableListOf()
                pagingState.value = PagingState.DONE
            } else {
                val combineContacts = arrayListOf<Contact>()
                val threshold = 100
                var lastCount = 0
                val numberOfIteration =
                    ceil((localContacts.size.toDouble()) / threshold.toDouble()).toInt()
                for (x in 1..numberOfIteration) {
                    val itemsToPost = localContacts.subList(
                        lastCount,
                        if ((x * threshold) > localContacts.size) localContacts.size else x * threshold
                    )
                    getY2YFromServer(itemsToPost) { contacts ->
                        contacts?.let { combineContacts.addAll(it) }
                        if (combineContacts.size >= localContacts.size) {
                            combineContacts.sortBy { it.title }
                            phoneContactLiveData.postValue(combineContacts)
                            pagingState.postValue(PagingState.DONE)
                        }
                    }

                    lastCount = x * threshold
                }
            }
        }
    }

    private suspend fun getY2YFromServer(
        localList: MutableList<Contact>,
        success: (List<Contact>?) -> Unit
    ) {
        when (val response =
            repository.getY2YBeneficiaries(localList)) {
            is RetroApiResponse.Success -> {
                success.invoke(response.data.data)
            }
            is RetroApiResponse.Error -> {

            }
        }
    }
}
