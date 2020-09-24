package co.yap.modules.dashboard.store.young.pincode

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.StringUtils
import javax.inject.Inject

class YoungCreatePinCodeVM @Inject constructor(
    override val state: IYoungPinCode.State
) :
    DaggerBaseViewModel<IYoungPinCode.State>(), IYoungPinCode.ViewModel {
    override fun isValidPassCode() : Boolean{
        val isSame = StringUtils.hasAllSameChars(state.passCode.value.toString())
        val isSequenced = StringUtils.isSequenced(state.passCode.value.toString())
        if (isSequenced) state.dialerError?.value =
            getString(Strings.screen_create_passcode_display_text_error_sequence)
        if (isSame) state.dialerError?.value =
            getString(Strings.screen_create_passcode_display_text_error_same_digits)
        return !isSame && !isSequenced
    }

    override fun handleOnClick(id: Int) {
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}