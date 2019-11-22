package co.yap.modules.dashboard.yapit.topup.addtopupcard.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.topup.addtopupcard.interfaces.IAddTopUpCard
import co.yap.modules.dashboard.yapit.topup.addtopupcard.states.AddTopUpCardState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.Session
import co.yap.networking.customers.requestdtos.CreateBeneficiaryRequest
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel

class AddTopUpCardViewModel(application: Application) :
    BaseViewModel<IAddTopUpCard.State>(application), IAddTopUpCard.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val state: AddTopUpCardState =
        AddTopUpCardState()
    override val repository: CustomersRepository = CustomersRepository
    override val isCardAdded: MutableLiveData<TopUpCard> = MutableLiveData()

    override fun addTopUpCard(sessionId: String, alias: String, color: String) {
        launch {
            state.loading = true
            when (val response = repository.createBeneficiary(
                CreateBeneficiaryRequest(
                    alias, color,
                    Session(sessionId)
                )
            )) {
                is RetroApiResponse.Success -> {
                    isCardAdded.value = response.data.data
                }

                is RetroApiResponse.Error -> {
                    isCardAdded.value = null
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }
}