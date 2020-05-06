package co.yap.modules.subaccounts.householdsetpin.setNewpin

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class HHSetPinVM @Inject constructor(override var state: IHHSetPin.State) :
    DaggerBaseViewModel<IHHSetPin.State>(), IHHSetPin.ViewModel,
    IRepositoryHolder<CardsRepository> {

    override var mobileNumber: String = ""
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let {
            state.setPinDataModel.value = it.getParcelable(SetPinDataModel::class.simpleName)
        }
    }

    override fun handleButtonPress(id: Int) {
        val validateAgg = Utils.validateAggressively(context, state.pinCode.value.toString())
        if (validateAgg.isEmpty()) {
            clickEvent.setValue(id)
        } else {
            state.dialerError.value = validateAgg
        }
    }

    override fun setPinRequest() {
        clickEvent.call()
    }

    override fun setCardPin() {
        launch {
            state.loading = true

//            TODO Please fetch latest serial number for household user

            var serialNumb: String
            MyUserManager.getPrimaryCard().let {
                serialNumb = MyUserManager.getPrimaryCard()?.cardSerialNumber.toString()
            }
            when (val response = repository.createCardPin(
                CreateCardPinRequest(state.pinCode.value.toString()),
                serialNumb
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(eventSuccess)
                }
                is RetroApiResponse.Error -> {
                    state.dialerError.value = response.error.message
                    clickEvent.setValue(eventFailure)
                }
            }
            state.loading = false
        }
    }

}