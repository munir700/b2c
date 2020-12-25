package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfo
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument
import co.yap.networking.customers.models.additionalinfo.AdditionalQuestion
import co.yap.networking.customers.responsedtos.additionalinfo.AdditionalInfo
import co.yap.networking.customers.responsedtos.additionalinfo.AdditionalInfoResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.enums.AdditionalInfoScreenType

class AdditionalInfoViewModel(application: Application) :
    BaseViewModel<IAdditionalInfo.State>(application = application),
    IAdditionalInfo.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val stepCount: MutableLiveData<Int> = MutableLiveData(0)
    override val state: IAdditionalInfo.State = AdditionalInfoState()
    override val additionalInfoResponse: MutableLiveData<AdditionalInfoResponse> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        getAdditionalInfo()
    }


    fun getAdditionalInfo() {
        launch {
            state.loading = true
            when (val response = repository.getAdditionalInfoRequired()) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    additionalInfoResponse.value = response.data
                    setSteps()
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    showToast(response.error.message)
                }
            }
//            if (additionalInfoResponse.value == null)
//            additionalInfoResponse.value = getMockData()
//            setSteps()
//            delay(5000)
//            state.loading = false

        }
    }

    private fun setSteps() {
        additionalInfoResponse.value?.data?.let { additionalInfo ->
            if (!additionalInfo.documentInfo.isNullOrEmpty() && !additionalInfo.textInfo.isNullOrEmpty()) {
                state.steps.set(2)
                state.screenType.set(AdditionalInfoScreenType.BOTH_SCREENS.name)
                state.documentList.addAll(additionalInfo.documentInfo as ArrayList<AdditionalDocument>)
                state.questionList.addAll(additionalInfo.textInfo as ArrayList<AdditionalQuestion>)
            } else if (!additionalInfo.documentInfo.isNullOrEmpty()) {
                state.steps.set(1)
                state.screenType.set(AdditionalInfoScreenType.DOCUMENT_SCREEN.name)
                state.documentList.addAll(additionalInfo.documentInfo as ArrayList<AdditionalDocument>)
            } else if (!

                additionalInfo.textInfo.isNullOrEmpty()
            ) {
                state.steps.set(1)
                state.screenType.set(AdditionalInfoScreenType.QUESTION_SCREEN.name)
                state.questionList.addAll(additionalInfo.textInfo as ArrayList<AdditionalQuestion>)
            } else {
                state.steps.set(0)
                state.screenType.set(AdditionalInfoScreenType.SUCCESS_SCREEN.name)
            }
        } ?: state.steps.set(0)

        if (state.steps.get() == 0) {
            state.screenType.set(AdditionalInfoScreenType.SUCCESS_SCREEN.name)
        }
    }

    private fun getMockData(): AdditionalInfoResponse {
        return AdditionalInfoResponse(getData())
    }

    private fun getData(): AdditionalInfo {
        val documentList: ArrayList<AdditionalDocument> = arrayListOf()
        documentList.add(AdditionalDocument(0, "Passport Copy", false, status =   "PENDING"))
        documentList.add(AdditionalDocument(0, "Visa Copy", false, status = "PENDING"))
//        list.add(AdditionalDocument(0, "Passport Copy", false))
        val questionList: ArrayList<AdditionalQuestion> = arrayListOf()
        questionList.add(
            AdditionalQuestion(
                0,
                false,
                questionFromCustomer = "Please tell us the name of the company you are currently employed with"
            )
        )
        return AdditionalInfo(documentList, questionList)
    }
}