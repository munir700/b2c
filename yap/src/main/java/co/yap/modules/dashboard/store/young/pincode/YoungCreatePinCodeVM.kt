package co.yap.modules.dashboard.store.young.pincode

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class YoungCreatePinCodeVM @Inject constructor(
    override val state: IYoungPinCode.State, override var validator: Validator?
) :
    DaggerBaseViewModel<IYoungPinCode.State>(), IYoungPinCode.ViewModel,IValidator {

    override fun handleOnClick(id: Int) {
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}