package co.yap.modules.dashboard.store.young.pincode

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository.getUserAddressRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungCreatePinCodeVM @Inject constructor(
    override val state: YoungCreatePinCodeState
) :
    HiltBaseViewModel<IYoungPinCode.State>(), IYoungPinCode.ViewModel, IValidator {

    override var validator: Validator? = Validator(null)

    override fun handleOnClick(id: Int) {
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        requestGetAddressForPhysicalCard {
        }
    }

    override fun requestGetAddressForPhysicalCard(
        apiResponse: ((Boolean) -> Unit?)?
    ) {
        launch {
            when (val response = getUserAddressRequest()) {
                is RetroApiResponse.Success -> {
                    state.address?.value = response.data.data
                    apiResponse?.invoke(true)
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    apiResponse?.invoke(false)
                }
            }
        }
    }
}