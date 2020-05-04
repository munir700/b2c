package co.yap.modules.subaccounts.householdsetpin.setNewpin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class HHSetPinVM @Inject constructor(override var state: IHHSetPin.State) :
    DaggerBaseViewModel<IHHSetPin.State>(), IHHSetPin.ViewModel,
    IRepositoryHolder<CardsRepository> {

    override var mobileNumber: String=""
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let {
            state.setPinDataModel.value = it.getParcelable(SetPinDataModel::class.simpleName)
        }
    }

    override fun handleButtonPress(id: Int, context: Context) {
        val validateAgg = Utils.validateAggressively(context, state.pinCode )
        if (validateAgg.isEmpty()) {
            clickEvent.setValue(id)
        }else{
            state.dialerError.set(validateAgg)
        }
    }

    override fun setPinRequest() {
        clickEvent.call()
    }

    override fun setCardPin() {
        launch {
            state.loading = true

//            TODO Please fetch latest serial number for household user

            var serialNumb:String
            MyUserManager.getPrimaryCard().let {
                serialNumb = MyUserManager.getPrimaryCard()?.cardSerialNumber.toString()
            }
            when (val response = repository.createCardPin(CreateCardPinRequest(state.pinCode), serialNumb)) {
                is RetroApiResponse.Success -> {
                    kotlinx.coroutines.delay(600)
                    clickEvent.setValue(EVENT_SET_CARD_PIN_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    kotlinx.coroutines.delay(600)
                    state.dialerError.set(response.error.message)
                    clickEvent.setValue(EVENT_SET_CARD_PIN_FAILURE)
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

}