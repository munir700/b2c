package co.yap.household.setpin.setnewpin

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import javax.inject.Inject

class HHSetPinVM @Inject constructor(override var state: IHHSetPin.State) :
    DaggerBaseViewModel<IHHSetPin.State>(), IHHSetPin.ViewModel,
    IRepositoryHolder<CardsRepository> {

    override var mobileNumber: String = ""
    override var errorEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let {
            state.setPinDataModel.value = it.getParcelable(SetPinDataModel::class.simpleName)
        }
    }

    override fun handlePressOnView(id: Int) {
        handleOnClick(id)
    }

    override fun handleOnClick(id: Int) {
        val validateAgg = Utils.validateAggressively(context, state.pinCode.value.toString())
        if (validateAgg.isEmpty()) {
            clickEvent?.setValue(id)
        } else {
            state.dialerError.value = validateAgg
        }
    }

    override fun setPinRequest() {
        clickEvent?.call()
    }

    override fun setCardPin() {
        launch {
            state.loading = true

            var cardSerialNumber: String
            SessionManager.getPrimaryCard().let {
                cardSerialNumber = it?.cardSerialNumber.toString()
            }

            when (val response = repository.createCardPin(
                CreateCardPinRequest(state.pinCode.value.toString()),
                cardSerialNumber
            )) {
                is RetroApiResponse.Success -> {
                    if (SessionManager.isExistingUser()) {
                        trackEvent(HHUserOnboardingEvents.HH_USER_EXISTING_ACCOUNT_ACTIVE.type)
                    } else {
                        trackEvent(HHUserOnboardingEvents.HH_USER_ACCOUNT_ACTIVE.type)
                    }
                    clickEvent?.setValue(eventSuccess)
                }
                is RetroApiResponse.Error -> {
                    state.dialerError.value = response.error.message
                    clickEvent?.setValue(eventFailure)
                }
            }
            state.loading = false
        }
    }

}