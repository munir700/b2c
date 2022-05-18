package co.yap.modules.dashboard.store.young.confirmrelationship

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungConfirmRelationshipVM @Inject constructor(
    override val state: YoungConfirmRelationshipState
) :
    HiltBaseViewModel<IYoungConfirmRelationship.State>(), IYoungConfirmRelationship.ViewModel,
    IValidator {
    override var validator: Validator? = Validator(null)


    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
