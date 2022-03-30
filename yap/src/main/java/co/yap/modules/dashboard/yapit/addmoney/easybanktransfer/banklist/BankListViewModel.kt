package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent

class BankListViewModel(application: Application) :
    AddMoneyBaseViewModel<IBankList.State>(application),
    IBankList.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val bankList: MutableLiveData<MutableList<BankListMainModel>> = MutableLiveData()
    override val bankListAdapter: BankListAdapter = BankListAdapter(mutableListOf(), null)
    private val leanTechRepository: LeanTechRepository = LeanTechRepository

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: IBankList.State = BankListState()

    override fun getBankList() {
        launch {
            when (val response = leanTechRepository.bankList()) {
                is RetroApiResponse.Success -> {
                    //did this to only verify null/empty logo
                    var list = response.data.data
                    list?.get(0)?.logo = "https://s3-eu-west-1.amazonaws.com//stg-yap-documents-public/profile_image/customer_data/1000001167/documents/PROFILE_PICTURE.jpeg"
                    list?.get(1)?.logo=""
                    bankList.postValue(response.data.data)
                }
                is RetroApiResponse.Error -> {
                    response.error
                }
            }
        }
    }
}