package co.yap.modules.dashboard.cards.addpaymentcard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.interfaces.IAddPaymentCard
import co.yap.modules.dashboard.cards.addpaymentcard.states.AddPaymentCardsState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class AddPaymentCardViewModel(application: Application) :
    BaseViewModel<IAddPaymentCard.State>(application),
    IAddPaymentCard.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override var physicalCardFee: String = ""
    override var virtualCardFee: String = ""
    override val repository: TransactionsRepository = TransactionsRepository
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: AddPaymentCardsState =
        AddPaymentCardsState()


    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }

    override fun onCreate() {
        super.onCreate()
        getVirtualCardFee()
        getPhysicalCardFee()
    }

    override fun getVirtualCardFee() {
        launch {
            state.loading = true
            when (val response = repository.getCardFee("virtual")) {
                is RetroApiResponse.Success -> {
                    virtualCardFee = response.data.data?.currency + " " + response.data.data?.amount
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun getPhysicalCardFee() {
        launch {
            when (val response = repository.getCardFee("physical")) {
                is RetroApiResponse.Success -> {
                    physicalCardFee =
                        response.data.data?.currency + " " + response.data.data?.amount
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }
}