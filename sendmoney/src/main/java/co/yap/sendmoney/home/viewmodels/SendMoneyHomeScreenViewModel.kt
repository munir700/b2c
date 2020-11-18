package co.yap.sendmoney.home.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.customers.responsedtos.sendmoney.GetAllBeneficiaryResponse
import co.yap.networking.customers.responsedtos.sendmoney.IBeneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.sendmoney.home.adapters.AllBeneficiariesAdapter
import co.yap.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.sendmoney.home.main.SMBeneficiaryParentBaseViewModel
import co.yap.sendmoney.home.states.SendMoneyHomeState
import co.yap.translation.Strings
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getLocalContacts
import co.yap.yapcore.helpers.extentions.parseRecentItems
import co.yap.yapcore.helpers.extentions.removeOwnContact
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.math.ceil

class SendMoneyHomeScreenViewModel(application: Application) :
    SMBeneficiaryParentBaseViewModel<ISendMoneyHome.State>(application), ISendMoneyHome.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: SendMoneyHomeState = SendMoneyHomeState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var pagingState: MutableLiveData<PagingState> = MutableLiveData()
    override val allBeneficiariesLiveData: MutableLiveData<List<Beneficiary>> = MutableLiveData()
    override var onDeleteSuccess: MutableLiveData<Int> = MutableLiveData()
    override var recentTransferData: MutableLiveData<List<Beneficiary>> = MutableLiveData()
    override val searchQuery: MutableLiveData<String> = MutableLiveData()
    override val isSearching: MutableLiveData<Boolean> = MutableLiveData()
    override var recentsAdapter: CoreRecentTransferAdapter = CoreRecentTransferAdapter(
        context,
        mutableListOf()
    )
    override var beneficiariesAdapter: AllBeneficiariesAdapter = AllBeneficiariesAdapter(
        mutableListOf()
    )

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        SessionManager.getCurrenciesFromServer { _, _ -> }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_send_money_display_text_title))
    }

    override fun requestAllBeneficiaries(sendMoneyType: String) {
        if (sendMoneyType == SendMoneyTransferType.ALL_Y2Y_SM.name) {
            getY2YAndSMBeneficiaries {
                beneficiariesAdapter.setList(it.sortedBy { beneficiary -> beneficiary.fullName })
            }
        } else {
            launch {
                state.loading = true
                when (val response = repository.getAllBeneficiaries()) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        val filteredList = getBeneficiariesOfType(sendMoneyType, response.data.data)
                        filteredList.parseRecentItems()

                        allBeneficiariesLiveData.value = filteredList
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    }
                }
            }
        }
    }

    override fun requestRecentBeneficiaries(sendMoneyType: String) {
        launch {
            when (val response = repository.getRecentBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    val filteredList = getBeneficiariesOfType(sendMoneyType, response.data.data)
                    if (filteredList.isNullOrEmpty())
                        state.isNoRecentBeneficiary.set(true)
                    else
                        state.isNoRecentBeneficiary.set(false)

                    filteredList.parseRecentItems()
                    recentsAdapter.setList(filteredList)
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }
    }

    override fun requestDeleteBeneficiary(beneficiaryId: Int) {
        launch {
            state.loading = true
            when (val response = repository.deleteBeneficiaryFromList(beneficiaryId.toString())) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.toast = "Deleted Successfully"
                    requestRecentBeneficiaries(state.sendMoneyType.get() ?: "")
                    onDeleteSuccess.setValue(111)
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }
    }

    private fun getBeneficiariesOfType(type: String, list: List<Beneficiary>): List<Beneficiary> {
        return when (type) {
            SendMoneyTransferType.HOME_COUNTRY.name -> {
                list.filter { it.country == SessionManager.user?.currentCustomer?.homeCountry }
            }
            SendMoneyTransferType.INTERNATIONAL.name -> {
                list.filter { (it.beneficiaryType == SendMoneyBeneficiaryType.RMT.type || it.beneficiaryType == SendMoneyBeneficiaryType.SWIFT.type) && it.country != SessionManager.user?.currentCustomer?.homeCountry }
            }
            SendMoneyTransferType.LOCAL.name -> {
                list.filter { it.beneficiaryType == SendMoneyBeneficiaryType.UAEFTS.type || it.beneficiaryType == SendMoneyBeneficiaryType.DOMESTIC.type }
            }
            else -> list
        }
    }

    private fun getY2YAndSMBeneficiaries(success: (ArrayList<IBeneficiary>) -> Unit) {
        fetchCombinedBeneficiariesApis { sendMoneyBeneficiariesResponse, y2yBeneficiaries ->
            launch(Dispatcher.Main) {
                val combinedList: ArrayList<IBeneficiary> = arrayListOf()
                when (sendMoneyBeneficiariesResponse) {
                    is RetroApiResponse.Success -> {
                        combinedList.addAll(
                            sendMoneyBeneficiariesResponse.data.data as ArrayList<IBeneficiary>
                        )
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                    }
                }
                y2yBeneficiaries?.forEach {
                    it.mobileNo =
                        Utils.getFormattedPhoneNumber(context, it.countryCode + it.mobileNo)
                }
                combinedList.addAll(y2yBeneficiaries?.filter { it.yapUser == true } as ArrayList<IBeneficiary>)
                success(combinedList)
            }
        }
    }

    private fun fetchCombinedBeneficiariesApis(
        responses: (RetroApiResponse<GetAllBeneficiaryResponse>?, List<Contact>?) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            coroutineScope {
                val deferredSM = async { repository.getAllBeneficiaries() }
                getLocalContactsFromServer {
                    async {
                        responses(deferredSM.await(), it)
                    }
                }
            }
        }
    }

    private suspend fun getLocalContactsFromServer(contactsList: (List<Contact>) -> Unit) {
        launch(Dispatcher.LongOperation) {
            val localContacts = getLocalContacts(context).removeOwnContact()
            if (localContacts.isEmpty()) {
                contactsList.invoke(mutableListOf())
            } else {
                val combineContacts = arrayListOf<Contact>()
                val threshold = 3000
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
                            contactsList.invoke(combineContacts)
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