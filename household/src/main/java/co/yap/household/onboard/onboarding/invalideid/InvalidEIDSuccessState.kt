package co.yap.household.onboard.onboarding.invalideid

import android.app.Application
import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.yapcore.BaseState

class InvalidEIDSuccessState(val application: Application) : BaseState(), IInvalidEIDSuccess.State {

    @get:Bindable
    override var name: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
}