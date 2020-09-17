package co.yap.modules.dashboard.store.young.confirmrelationship

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class YoungConfirmRelationshipVM @Inject constructor(
    override val state: IYoungConfirmRelationship.State,
    override var validator: Validator?
) :
    DaggerBaseViewModel<IYoungConfirmRelationship.State>(), IYoungConfirmRelationship.ViewModel,
    IValidator {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
