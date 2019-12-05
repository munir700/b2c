package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import android.app.Application
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.translation.Strings.screen_international_funds_transfer_display_text_fee
import co.yap.translation.Translator
import co.yap.yapcore.BaseState

class InternationalFundsTransferState(val application: Application) : BaseState(),
    IInternationalFundsTransfer.State {

    @get:Bindable
    override var transferFee: String = "155"
        set(value) {
//            field = value
            field = Translator.getString(
                application.applicationContext,
                screen_international_funds_transfer_display_text_fee
            ) + value
            notifyPropertyChanged(BR.transferFee)

        }

    @get:Bindable
    override var nameInitialsVisibility: Int = View.VISIBLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.nameInitialsVisibility)
        }

    @get:Bindable
    override var beneficiaryPicture: String =
        "https://scoopak.com/wp-content/uploads/2013/06/free-hd-natural-wallpapers-download-for-pc.jpg"
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryPicture)
            if (!beneficiaryPicture.isNullOrEmpty()) {
                nameInitialsVisibility = View.GONE
            } else {
                nameInitialsVisibility = View.VISIBLE
            }

        }

    @get:Bindable
    override var beneficiaryName: String = "Jonathan Newport"
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryName)
        }

    @get:Bindable
    override var senderCurrency: String = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.senderCurrency)
        }

    @get:Bindable
    override var beneficiaryCurrency: String = "CAD"
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryCurrency)
        }

    @get:Bindable
    override var beneficiaryCountry: String = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryCountry)
        }

    @get:Bindable
    override var senderAmount: String = "0.00"
        set(value) {
            field = value
            notifyPropertyChanged(BR.senderAmount)
            validate()
        }

    @get:Bindable
    override var beneficiaryAmount: String = "0.00"
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryAmount)
            validate()
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    fun validate() {
        if (!senderAmount.isNullOrEmpty() && !beneficiaryAmount.isNullOrEmpty()/* &&  reason must be selected as well */) {

            valid = true
        }
    }
}