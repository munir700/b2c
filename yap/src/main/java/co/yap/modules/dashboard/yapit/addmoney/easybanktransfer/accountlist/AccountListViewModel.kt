package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.extentions.toast

class AccountListViewModel(application: Application) :
    AddMoneyBaseViewModel<IAccountList.State>(application),
    IAccountList.ViewModel {

    override var accountList: MutableLiveData<MutableList<Any>> = MutableLiveData()
    private val leanTechRepository: LeanTechRepository = LeanTechRepository
    override val state: IAccountList.State = AccountListState()

    override fun getAccountList() {
        launch {
            state.loading = true
            when (val response = leanTechRepository.accountList()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let { list ->
                        val accountListTemp: MutableList<Any> = mutableListOf()
                        list.sortBy { it.bank?.name }
                        list.forEach { value ->
                            with(value.bank){
                                this?.status = value.status
                                this?.let { accountListTemp.add(it) }
                            }
                            with(value.leanCustomerAccounts){
                                sortBy { it.accountName }
                                forEach { v ->
                                    accountListTemp.add(v)
                                }
                            }
                        }
                        accountList.postValue(accountListTemp)
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    toast(context, response.error.message)
                    state.loading = false
                }
            }
        }
    }
}