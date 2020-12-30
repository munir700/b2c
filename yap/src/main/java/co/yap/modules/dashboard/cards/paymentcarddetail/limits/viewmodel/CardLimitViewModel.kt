package co.yap.modules.dashboard.cards.paymentcarddetail.limits.viewmodel

import android.app.Application
import android.widget.CompoundButton
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.interfaces.ICardLimits
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.states.CardLimitState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.leanplum.CardEvents
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent

class CardLimitViewModel(application: Application) :
    BaseViewModel<ICardLimits.State>(application),
    ICardLimits.ViewModel, IRepositoryHolder<CardsRepository> {

    override val state: ICardLimits.State = CardLimitState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    fun onSwitchChanged(switch: CompoundButton, isChecked: Boolean) {
        if (switch.isPressed) {
            when (switch.id) {
                R.id.swWithdrawal -> {
                    if(isChecked) {
                        trackEvent(CardEvents.CARD_CONTROL_ATM_ON.type)
                    } else {
                        trackEvent(CardEvents.CARD_CONTROL_ATM_OFF.type)
                    }
                    updateAllowAtm()
                }
                R.id.swOnlineTra -> {
                    if(isChecked) {
                        trackEvent(CardEvents.CARD_CONTROL_ONLINE_ON.type)
                    } else {
                        trackEvent(CardEvents.CARD_CONTROL_ONLINE_OFF.type)
                    }
                    updateOnlineTransaction()
                }
                R.id.swAbroad -> {
                    if(isChecked) {
                        trackEvent(CardEvents.CARD_CONTROL_INTERNATIONAL_ON.type)
                    } else {
                        trackEvent(CardEvents.CARD_CONTROL_INTERNATIONAL_OFF.type)
                    }
                    updateAbroadPayment()
                }
                R.id.swRetail -> {
                    if(isChecked) {
                        trackEvent(CardEvents.CARD_CONTROL_POS_ON.type)
                    } else {
                        trackEvent(CardEvents.CARD_CONTROL_POS_OFF.type)
                    }
                    updateRetailPayment()
                }
            }
        }
    }

    private fun updateAllowAtm() {
        launch {
            state.loading = true
            when (val response =
                repository.configAllowAtm(CardLimitConfigRequest(state.card.get()!!.cardSerialNumber))) {
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                    state.card.get()?.also {
                        it.retailPaymentAllowed = it.retailPaymentAllowed
                        state.card.notifyChange()
                    }
                }

                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.card.get()?.atmAllowed = !state.card.get()!!.atmAllowed
                }
            }
        }
    }

    private fun updateOnlineTransaction() {
        launch {
            state.loading = true
            when (val response =
                repository.configOnlineBanking(CardLimitConfigRequest(state.card.get()!!.cardSerialNumber))) {
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                    state.card.get()?.also {
                        it.retailPaymentAllowed = it.retailPaymentAllowed
                        state.card.notifyChange()
                    }

                }
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.card.get()?.onlineBankingAllowed =
                        !state.card.get()!!.onlineBankingAllowed
                }
            }
        }
    }

    private fun updateRetailPayment() {
        launch {
            state.loading = true
            when (val response =
                repository.configRetailPayment(CardLimitConfigRequest(state.card.get()!!.cardSerialNumber))) {
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                    state.card.get()?.also {
                        it.retailPaymentAllowed = it.retailPaymentAllowed
                        state.card.notifyChange()
                    }

                }
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.card.get()?.retailPaymentAllowed =
                        !state.card.get()!!.retailPaymentAllowed
                }
            }
        }
    }

    private fun updateAbroadPayment() {
        launch {
            state.loading = true
            when (val response =
                repository.configAbroadPayment(CardLimitConfigRequest(state.card.get()!!.cardSerialNumber))) {
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                    state.card.get()?.also {
                        it.retailPaymentAllowed = it.retailPaymentAllowed
                        state.card.notifyChange()
                    }
                }
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.card.get()?.paymentAbroadAllowed =
                        !state.card.get()!!.paymentAbroadAllowed

                }
            }
        }
    }

}