package co.yap.modules.dashboard.yapit.topup.topupcards

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TopUpCardsViewModel(application: Application) :
    BaseViewModel<ITopUpCards.State>(application),
    ITopUpCards.ViewModel, IRepositoryHolder<CustomersRepository> {

    override var remainingCardsLimit: Int = 0

    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ITopUpCards.State = TopUpCardsState()
    override val repository: CustomersRepository = CustomersRepository
    override val topUpCards: MutableLiveData<List<TopUpCard>> = MutableLiveData()


    override fun onResume() {
        super.onResume()
        getCardsLimit()

    }

    override fun onCreate() {
        super.onCreate()
        getPaymentCards()


    }

    override fun handlePressOnBackButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun getCardsLimit() {
        launch {
            when (val response = repository.getCardsLimit()) {
                is RetroApiResponse.Success -> {
                    remainingCardsLimit = response.data.data.remaining
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
        }
    }

    override fun getPaymentCards() {
        launch {
            state.loading = true
            when (val response = repository.getTopUpBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    if (state.enableAddCard.get())
                        response.data.data.add(TopUpCard(alias = "addCard"))
                    topUpCards.value = response.data.data
                }

                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun updateCardCount() {
        topUpCards.value?.size?.let {
            val size = it - if (state.enableAddCard.get()) 1 else 0
            val message = Translator.getString(
                context,
                R.string.screen_cards_display_text_cards_count
            ).replace("%d", size.toString())
            when (size) {
                0 -> {
                    state.noOfCard.set("No cards added yet")
                    state.message.set("Add a card to top up")
                }
                1 -> state.noOfCard.set(message.substring(0, message.length - 1))
                else -> state.noOfCard.set(message)
            }
        }
    }

}