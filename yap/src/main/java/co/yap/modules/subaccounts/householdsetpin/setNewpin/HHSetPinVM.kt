package co.yap.modules.subaccounts.householdsetpin.setNewpin

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.extentions.toast
import javax.inject.Inject

class HHSetPinVM @Inject constructor(override var state: IHHSetPin.State) :
    DaggerBaseViewModel<IHHSetPin.State>(), IHHSetPin.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let {
            state.setPinDataModel.value = it.getParcelable(SetPinDataModel::class.simpleName)
        }
    }

    override fun handleButtonPress(id: Int) {
        clickEvent.setValue(id)
    }

    override fun setPinRequest() {
        clickEvent.call()
    }

}