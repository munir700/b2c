package co.yap.modules.dashboard.cards.paymentcarddetail.limits.viewmodel

import android.app.Application
import android.widget.CompoundButton
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.interfaces.ICardLimits
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.states.CardLimitState
import co.yap.networking.customers.requestdtos.SendVerificationEmailRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class CardLimitViewModel(application: Application) :
    BaseViewModel<ICardLimits.State>(application),
    ICardLimits.ViewModel {

    override val state: ICardLimits.State = CardLimitState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    fun onSwitchChanged(switch: CompoundButton, isChecked: Boolean) {
        when (switch.id) {
            R.id.swWithdrawal -> {

            }
            R.id.swOnlineTra -> {

            }
            R.id.swAbroad -> {

            }
            R.id.swRetail -> {

            }
        }
    }

    fun updateLimitsRequest() {
//        launch {
//            state.loading = true
//            when (val response = repository.sendVerificationEmail(
//                SendVerificationEmailRequest(
//                    state.twoWayTextWatcher,
//                    parentViewModel!!.onboardingData.accountType.toString()
//                )
//            )) {
//                is RetroApiResponse.Error -> {
//                    state.emailError = response.error.message
//                    state.loading = false
//
//                }
//                is RetroApiResponse.Success -> {
//                    signUp()
//                }
//            }
//        }
    }

}