package co.yap.modules.dashboard.cards.paymentcarddetail.limits.viewmodel

import android.app.Application
import android.widget.CompoundButton
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.interfaces.ICardLimits
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.states.CardLimitState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.ConfigAtm
import co.yap.networking.customers.requestdtos.SendVerificationEmailRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

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
                    allAtm(isChecked)
                }
                R.id.swOnlineTra -> {

                }
                R.id.swAbroad -> {

                }
                R.id.swRetail -> {

                }
            }
        }
    }

    private fun allAtm(allow: Boolean) {
        launch {
            state.loading = true
            when (val response = repository.configAllowAtm(ConfigAtm(state.card.get()!!.cardSerialNumber) )) {
                is RetroApiResponse.Error -> {
                    response.error.message
                    state.loading = false

                }
                is RetroApiResponse.Success -> {
                    val data = response.data
                }
            }
        }
    }

}