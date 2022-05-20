package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechApi
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toast

class AccountListViewModel(application: Application) :
    AddMoneyBaseViewModel<IAccountList.State>(application),
    IAccountList.ViewModel {
    private val leanTechRepository: LeanTechApi = LeanTechRepository
    override val state: IAccountList.State = AccountListState()
    override val leanOnBoardModel: MutableLiveData<LeanOnBoardModel> = MutableLiveData()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var accountList: MutableLiveData<MutableList<AccountsListModel>> = MutableLiveData()
    override var isListClicked: Boolean = false
    override var leanCustomerAccounts: AccountsListModel = AccountsListModel()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
    
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
                            val accountListTemp: MutableList<AccountsListModel> = mutableListOf()
                            var bankListMainModel = BankListMainModel()
                            list.sortBy { it.bank?.name }
                            list.forEach { value ->
                                with(value.bank) {
                                    this?.status = value.status
                                    this?.let { bankListMainModel = it }
                                    accountListTemp.add(
                                        AccountsListModel(
                                            null,
                                            bankListMainModel
                                        )
                                    ) //to use it for list header
                                }
                                with(value.leanCustomerAccounts) {
                                    sortBy { it.accountName }
                                    forEach { leanCustomerAccount ->
                                        accountListTemp.add(
                                            AccountsListModel(
                                                leanCustomerAccount,
                                                bankListMainModel
                                            )
                                        )
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