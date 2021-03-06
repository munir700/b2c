package co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.addpaymentcard.main.interfaces.IAddPaymentCard
import co.yap.modules.dashboard.cards.addpaymentcard.main.states.AddPaymentCardsState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.VirtualCardDesigns
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent

class AddPaymentCardViewModel(application: Application) :
    BaseViewModel<IAddPaymentCard.State>(application),
    IAddPaymentCard.ViewModel, IRepositoryHolder<CardsRepository> {
    override var physicalCardFee: String = ""
    override var virtualCardFee: String = ""
    override val repository: CardsRepository = CardsRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: AddPaymentCardsState = AddPaymentCardsState()
    override var virtualCardDesignsList: ArrayList<VirtualCardDesigns> = arrayListOf()
    override var selectedVirtualCard: VirtualCardDesigns? = null
    override var selectedCardName = ObservableField("")
    override var isFromBlockCard: ObservableField<Boolean> = ObservableField(false)
    override var selectedVirtualCardPosition: ObservableField<Int> = ObservableField(0)

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getVirtualCardDesigns(success: () -> Unit) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getVirtualCardDesigns()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        virtualCardDesignsList =
                            response.data.data as ArrayList<VirtualCardDesigns>?
                                ?: arrayListOf()

                        success.invoke()
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = response.error.message
                        state.viewState.value = false
                        success.invoke()
                    }
                }
            }
        }
    }
}