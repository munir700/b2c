package co.yap.modules.kyc.amendments.passportactivity

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState

class PassportState : BaseState(), IPassport.State {
    override var rightIcon: ObservableBoolean = ObservableBoolean(false)
    override var leftIcon: ObservableBoolean = ObservableBoolean(false)
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(false)

    @get:Bindable
    override var totalProgress: Int = 100
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalProgress)
        }

    @get:Bindable
    override var currentProgress: Int = 40
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentProgress)
        }
}