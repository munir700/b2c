package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalTransactionConfirmation
import co.yap.yapcore.BaseState

class InternationalTransactionConfirmationState : BaseState(),
    IInternationalTransactionConfirmation.State {
    @get:Bindable
    override var name: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
    @get:Bindable
    override var picture: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.picture)
        }
    @get:Bindable
    override var position: Int? = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
    @get:Bindable
    override var flagLayoutVisibility: Boolean? = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.flagLayoutVisibility)
        }
    @get:Bindable
    override var transferDescription: String? =
        "Your transfer of AED 1000 to Jonathan will be sent at the exchange rage of X AED to X CAD"
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferDescription)
        }
    @get:Bindable
    override var referenceNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.referenceNumber)
        }
}