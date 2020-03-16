package co.yap.sendMoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ITransferSuccess
import co.yap.yapcore.BaseState

class TransferSuccessState : BaseState(), ITransferSuccess.State {

    @get:Bindable
    override var transferType: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferType)

        }
    @get:Bindable
    override var successHeader: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.successHeader)
        }

    @get:Bindable
    override var amount: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.amount)

        }


    @get:Bindable
    override var currency: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)

        }

    @get:Bindable
    override var flag: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.flag)

        }


    @get:Bindable
    override var name: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    /*   @get:Bindable
       override var picture: String =
           "https://scoopak.com/wp-content/uploads/2013/06/free-hd-natural-wallpapers-download-for-pc.jpg"
           set(value) {
               field = value
               notifyPropertyChanged(BR.picture)

           }*/
    @get:Bindable
    override var picture: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.picture)
        }

    @get:Bindable
    override var referenceNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.referenceNumber)

        }
    @get:Bindable
    override var flagLayoutVisibility: Boolean? = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.flagLayoutVisibility)
        }
    @get:Bindable
    override var position: Int? = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
    @get:Bindable
    override var locationLayoutVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.locationLayoutVisibility)
        }
    @get:Bindable
    override var beneficiaryCountry: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryCountry)
        }
    @get:Bindable
    override var transferAmountHeading: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferAmountHeading)
        }
    @get:Bindable
    override var buttonTitle: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonTitle)
        }
    @get:Bindable
    override var availableBalanceString: CharSequence? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalanceString)
        }

    @get:Bindable
    override var pickUpAgentLocationAddress: String? =
        ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.pickUpAgentLocationAddress)

        }

    override var cutOffTimeMsg: ObservableField<String> = ObservableField()
}