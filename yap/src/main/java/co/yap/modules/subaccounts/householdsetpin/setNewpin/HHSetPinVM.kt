package co.yap.modules.subaccounts.householdsetpin.setNewpin

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.extentions.toast
import javax.inject.Inject

class HHSetPinVM @Inject constructor(override var state: IHHSetPin.State) :
    DaggerBaseViewModel<IHHSetPin.State>(), IHHSetPin.ViewModel {

    override var mobileNumber: String=""
    override var pincode: String = ""
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: SingleClickEvent = SingleClickEvent()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let {
            state.setPinDataModel.value = it.getParcelable(SetPinDataModel::class.simpleName)
            state.setPinDataModel.value?.setPinTitle = getString(Strings.screen_set_card_pin_display_text_title)
            state.setPinDataModel.value?.buttonTitle = getString(Strings.screen_set_card_pin_button_create_pin)
        }
    }

    override fun handleButtonPress(id: Int) {
        clickEvent.setValue(id)
    }

    override fun setPinRequest() {
        clickEvent.call()
    }

}