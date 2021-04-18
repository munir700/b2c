package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IWaitingList
import co.yap.modules.onboarding.states.WaitingListState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent

class WaitingListViewModel(application: Application) :
    BaseViewModel<IWaitingList.State>(application), IWaitingList.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: WaitingListState = WaitingListState()

    override fun handlePressOnNext(id: Int) {
        clickEvent.setValue(id)
    }

    override val repository: CustomersRepository
        get() = CustomersRepository

    override fun requestWaitingRanking(showNotification: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.getWaitingRanking()) {
                is RetroApiResponse.Success -> {
                    state.waitingBehind?.set(response.data.data?.waitingBehind ?: "0")
                    state.rank?.set(response.data.data?.rank ?: "0")
                    state.jump?.set(response.data.data?.jump ?: "0")
                    state.gainPoints?.set(response.data.data?.gainPoints ?: "0")
                    if (response.data.data?.viewable == true) {
                        stopRankingMsgRequest()
                        showNotification.invoke()
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun stopRankingMsgRequest() {
        launch(Dispatcher.Background) {
            when (repository.stopRankingMsgRequest()) {
                is RetroApiResponse.Success -> {
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }
}
