package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfo
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoState
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument
import co.yap.networking.customers.models.additionalinfo.AdditionalQuestion
import co.yap.networking.customers.responsedtos.additionalinfo.AdditionalInfo
import co.yap.networking.customers.responsedtos.additionalinfo.AdditionalInfoResponse
import co.yap.yapcore.BaseViewModel
import kotlinx.coroutines.delay

class AdditionalInfoViewModel(application: Application) :
    BaseViewModel<IAdditionalInfo.State>(application = application),
    IAdditionalInfo.ViewModel {
    override val stepCount: MutableLiveData<Int> = MutableLiveData(0)
    override val state: IAdditionalInfo.State = AdditionalInfoState()

    override fun onCreate() {
        super.onCreate()
        getAdditionalInfo()
    }


    fun getAdditionalInfo() {
        launch {
            state.loading = true
            getMockData()
            delay(3000)
            state.loading = false
        }
    }

    private fun getMockData() {
        val additionalInfoResponse: AdditionalInfoResponse = AdditionalInfoResponse(getData())
    }

    private fun getData(): AdditionalInfo {
        val documentList: ArrayList<AdditionalDocument> = arrayListOf()
        documentList.add(AdditionalDocument(0, "Passport Copy", false))
        documentList.add(AdditionalDocument(0, "Visa Copy", false))
//        list.add(AdditionalDocument(0, "Passport Copy", false))
        val questionList: ArrayList<AdditionalQuestion> = arrayListOf()
        questionList.add(AdditionalQuestion(0, false, questionFromCustomer = ""))
        return AdditionalInfo(documentList, questionList)
    }
}