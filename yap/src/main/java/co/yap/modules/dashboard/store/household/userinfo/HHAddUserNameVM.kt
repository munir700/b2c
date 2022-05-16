package co.yap.modules.dashboard.store.household.userinfo

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHAddUserNameVM @Inject constructor(override val state: HHAddUserNameState) :
    HiltBaseViewModel<IHHAddUserName.State>(), IHHAddUserName.ViewModel, IValidator {
    //TODO: Add this dependency in Hilt Graph after migration completes.
    override var validator: Validator? = Validator(null)
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
