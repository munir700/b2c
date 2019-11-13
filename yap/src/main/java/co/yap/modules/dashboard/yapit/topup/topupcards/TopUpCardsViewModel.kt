package co.yap.modules.dashboard.yapit.topup.topupcards

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
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
}