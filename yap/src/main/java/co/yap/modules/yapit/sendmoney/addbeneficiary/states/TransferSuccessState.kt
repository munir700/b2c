package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ITransferSuccess
import co.yap.yapcore.BaseState

class TransferSuccessState : BaseState(), ITransferSuccess.State {

    @get:Bindable
    override var pickUpAgentLocation: String = "nick name"
        set(value) {
            field = value
            notifyPropertyChanged(BR.pickUpAgentLocation)

        }

    @get:Bindable
    override var transferType: String = "bank trasfer"
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferType)

        }

    @get:Bindable
    override var amount: String = " 700"
        set(value) {
            field = value
            notifyPropertyChanged(BR.amount)

        }


    @get:Bindable
    override var currency: String = "AED "
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)

        }

    @get:Bindable
    override var flag: String = "flag"
        set(value) {
            field = value
            notifyPropertyChanged(BR.flag)

        }


    @get:Bindable
    override var name: String = "Bernard Erickson"
        set(value) {
            field = value
            notifyPropertyChanged(BR.flag)

        }

    @get:Bindable
    override var picture: String = "picture"
        set(value) {
            field = value
            notifyPropertyChanged(BR.picture)

        }

    @get:Bindable
    override var referenceNumber: String = "00313249457"
        set(value) {
            field = value
            notifyPropertyChanged(BR.referenceNumber)

        }

    @get:Bindable
    override var pickUpAgentLocationAddress: String =
        "Exchange house 1, Exchange house 2, Exhange house 3"
        set(value) {
            field = value
            notifyPropertyChanged(BR.pickUpAgentLocationAddress)

        }
}