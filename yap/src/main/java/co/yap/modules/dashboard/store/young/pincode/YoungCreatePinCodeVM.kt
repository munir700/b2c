package co.yap.modules.dashboard.store.young.pincode

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository.getUserAddressRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class YoungCreatePinCodeVM @Inject constructor(
    override val state: IYoungPinCode.State, override var validator: Validator?
) :
    DaggerBaseViewModel<IYoungPinCode.State>(), IYoungPinCode.ViewModel, IValidator {

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