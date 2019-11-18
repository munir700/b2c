package co.yap.modules.dashboard.yapit.topup.topupcards

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.yapcore.BaseState

class TopUpCardsState : BaseState(), ITopUpCards.State {

    override val valid: ObservableField<Boolean> = ObservableField(true)
    override val enableAddCard: ObservableBoolean = ObservableBoolean(true)
    override var noOfCard: ObservableField<String> = ObservableField("")
    override var alias: ObservableField<String> = ObservableField("")
    override var message: ObservableField<String> =
        ObservableField("Choose which card you want to top up with")

    @get:Bindable
    override var tootlBarTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.tootlBarTitle)

        }

    @get:Bindable
    override var tootlBarVisibility: Int = 0x00000000
        set(value) {
            field = value
            notifyPropertyChanged(BR.tootlBarVisibility)

        }

    @get:Bindable
    override var rightButtonVisibility: Int = 0x00000000
        set(value) {
            field = value
            notifyPropertyChanged(BR.rightButtonVisibility)
        }

    @get:Bindable
    override var leftButtonVisibility: Int = 0x00000000
        set(value) {
            field = value
            notifyPropertyChanged(BR.leftButtonVisibility)
        }
}