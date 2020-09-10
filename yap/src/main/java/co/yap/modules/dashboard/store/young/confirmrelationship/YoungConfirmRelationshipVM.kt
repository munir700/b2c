package co.yap.modules.dashboard.store.young.confirmrelationship

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class YoungConfirmRelationshipVM @Inject constructor(override val state: IYoungConfirmRelationship.State,  override var validator: Validator?) :
    DaggerBaseViewModel<IYoungConfirmRelationship.State>(), IYoungConfirmRelationship.ViewModel ,
    IValidator {
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}