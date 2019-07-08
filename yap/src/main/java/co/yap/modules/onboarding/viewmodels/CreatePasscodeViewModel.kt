package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import co.yap.R
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.modules.onboarding.states.CreatePasscodeState
import co.yap.yapcore.BaseViewModel

class CreatePasscodeViewModel(application: Application) : BaseViewModel<ICreatePasscode.State>(application),
    ICreatePasscode.ViewModel {
    override fun handlePressOnCreatePasscodeButton() {
//        state.dialerError="button clicked"
        state.dialerError=state.passcode

    }

    override val state: CreatePasscodeState = CreatePasscodeState()

}