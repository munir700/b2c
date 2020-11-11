package co.yap.sendmoney.home.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.sendmoney.home.adapters.RecentTransferAdaptor
import co.yap.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.sendmoney.home.states.SendMoneyHomeState
import co.yap.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.extentions.parseRecentItems
import co.yap.yapcore.managers.SessionManager


class SendMoneyHomeScreenViewModel(application: Application) :
    SendMoneyBaseViewModel<ISendMoneyHome.State>(application), ISendMoneyHome.ViewModel,
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


    override fun getState(): LiveData<PagingState> {
        return pagingState
    }

    override fun requestAllBeneficiaries(sendMoneyType: String) {
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

    private fun getBeneficiariesOfType(type: String, list: List<Beneficiary>): List<Beneficiary> {
        return when (type) {
            SendMoneyTransferType.HOME_COUNTRY.name -> {
                list.filter { it.country == SessionManager.user?.currentCustomer?.homeCountry }
            }
            SendMoneyTransferType.INTERNATIONAL.name -> {
                list.filter { it.beneficiaryType == SendMoneyBeneficiaryType.RMT.type || it.beneficiaryType == SendMoneyBeneficiaryType.SWIFT.type }
            }
            SendMoneyTransferType.LOCAL.name -> {
                list.filter { it.beneficiaryType == SendMoneyBeneficiaryType.UAEFTS.type || it.beneficiaryType == SendMoneyBeneficiaryType.DOMESTIC.type }
            }
            else -> list
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
}