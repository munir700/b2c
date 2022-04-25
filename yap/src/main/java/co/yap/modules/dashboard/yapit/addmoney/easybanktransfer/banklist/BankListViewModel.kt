package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.extentions.toast

class BankListViewModel(application: Application) :
    AddMoneyBaseViewModel<IBankList.State>(application),
    IBankList.ViewModel {
    override val bankList: MutableLiveData<MutableList<BankListMainModel>> = MutableLiveData()
    override val bankListAdapter: BankListAdapter = BankListAdapter(mutableListOf(), null)
    override var leanOnBoardModel: LeanOnBoardModel = LeanOnBoardModel()
    private val leanTechRepository: LeanTechRepository = LeanTechRepository

    override val state: IBankList.State = BankListState()

    override fun getBankList() {
        launch {
            state.loading=true
            when (val response = leanTechRepository.bankList()) {
                is RetroApiResponse.Success -> {
                    state.loading=false
                    bankList.postValue(response.data.data)
                }
                is RetroApiResponse.Error -> {
                    state.loading=false
                    toast(context,response.error.message)
                }
            }
        }
    }
}