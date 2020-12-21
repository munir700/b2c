package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoQuestion
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoQuestionState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadAdditionalInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse

class AdditionalInfoQuestionViewModel(application: Application) :
    AdditionalInfoBaseViewModel<IAdditionalInfoQuestion.State>(application),
    IAdditionalInfoQuestion.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val state: IAdditionalInfoQuestion.State = AdditionalInfoQuestionState(application)
    override fun onCreate() {
        super.onCreate()
        setTitle("Final Question")
        setSubTitle(getQuestionList().firstOrNull()?.questionFromCustomer ?: "")
    }

    override fun moveToNext() {
        moveStep()
    }

    override fun uploadAnswer(uploadAdditionalInfo: UploadAdditionalInfo, success: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.uploadAdditionalQuestion(
                uploadAdditionalInfo
            )) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    success()
                }
                is RetroApiResponse.Error -> {
                    showToast(response.error.message)
                    state.loading = false
                }
            }
        }
    }
}