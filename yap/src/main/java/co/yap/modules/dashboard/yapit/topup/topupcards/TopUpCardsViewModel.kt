package co.yap.modules.dashboard.yapit.topup.topupcards

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.Session
import co.yap.networking.customers.requestdtos.CreateBeneficiaryRequest
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

class TopUpCardsViewModel(application: Application) :
    BaseViewModel<ITopUpCards.State>(application),
    ITopUpCards.ViewModel, IRepositoryHolder<CustomersRepository> {

    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ITopUpCards.State = TopUpCardsState()
    override val repository: CustomersRepository = CustomersRepository
    override val topUpCards: MutableLiveData<List<TopUpCard>> = MutableLiveData()

    init {
        state.enableAddCard.set(
            MyUserManager.user?.notificationStatuses.equals(co.yap.modules.onboarding.constants.Constants.USER_STATUS_CARD_ACTIVATED)
        )
    }

    override fun onResume() {
        super.onResume()
        state.enableAddCard.set(
            MyUserManager.user?.notificationStatuses.equals(co.yap.modules.onboarding.constants.Constants.USER_STATUS_CARD_ACTIVATED)
        )
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

    override fun getPaymentCards() {
        launch {
            state.loading = true
            when (val response = repository.getTopUpBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    if (state.enableAddCard.get())
                        response.data.data.add(getAddCard())
                    topUpCards.value = response.data.data
                }

                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun updateCardCount(size: Int) {
        val message = Translator.getString(
            context,
            R.string.screen_cards_display_text_cards_count
        ).replace("%d", size.toString())
        when (size) {
            0 -> state.message.set("Add a card to top up")
            1 -> state.noOfCard.set(message.substring(0, message.length - 1))
            else -> state.noOfCard.set(message)
        }
    }

    private fun getAddCard(): TopUpCard {
        return TopUpCard(
            "",
            "",
            "",
            "",
            "addCard",
            ""
        )
    }

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

                }

                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }
}