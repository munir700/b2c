package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechApi
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toast

class AccountListViewModel(application: Application) :
    AddMoneyBaseViewModel<IAccountList.State>(application),
    IAccountList.ViewModel {

    private val leanTechRepository: LeanTechApi = LeanTechRepository
    override var accountList: MutableLiveData<MutableList<Any>> = MutableLiveData()
    override var accountListAdapter: AccountListAdapter = AccountListAdapter(mutableListOf(), null)
    override val state: IAccountList.State = AccountListState()
    override val leanOnBoardModel: MutableLiveData<LeanOnBoardModel> = MutableLiveData()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var customerId: String? = ""
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    //will change them in parallel call
    override fun onboardUser() {
        launch {
            when (val response = leanTechRepository.onBoardUser()) {
                is RetroApiResponse.Success -> {
                    leanOnBoardModel.postValue(response.data.data)
                }
                is RetroApiResponse.Error -> {
                    toast(context, response.error.message)
                }
            }
        }
    }

    override fun setMultiState() {
        state.stateLiveData?.value = State.loading("")
        getAccountList()
    }

    override fun getAccountList() {
        launch {
            when (val response = leanTechRepository.accountList()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let { list ->
                        if (list.isNullOrEmpty()) {
                            setStateValue(State.empty(""))
                        } else {
                            setStateValue(State.success(""))
                            val accountListTemp: MutableList<Any> = mutableListOf()
                            list.sortBy { it.bank?.name }
                            list.forEach { value ->
                                with(value.bank) {
                                    this?.status = value.status
                                    this?.let { accountListTemp.add(it) }
                                }
                                with(value.leanCustomerAccounts) {
                                    sortBy { it.accountName }
                                    forEach { v ->
                                        accountListTemp.add(v)
                                    }
                                }
                            }
                            accountList.postValue(accountListTemp)
                        }
                    } ?: setStateValue(State.empty(""))
                }
                is RetroApiResponse.Error -> {
                    toast(context, response.error.message)
                }
            }
        }
    }

    fun setStateValue(stateValue: State) {
        state.stateLiveData?.value = stateValue
    }
}