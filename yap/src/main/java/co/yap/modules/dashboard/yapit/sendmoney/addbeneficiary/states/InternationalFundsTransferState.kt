package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import android.app.Application
import android.text.SpannableStringBuilder
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.yapcore.BaseState

class InternationalFundsTransferState(val application: Application) : BaseState(),
    IInternationalFundsTransfer.State {

    @get:Bindable
    override var transferFee: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferFee)

        }
    @get:Bindable
    override var transferFeeSpannable: SpannableStringBuilder?= SpannableStringBuilder("")
        set(value) {
            field=value
            notifyPropertyChanged(BR.transferFeeSpannable)
        }
    @get:Bindable
    override var fxRateAmount: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fxRateAmount)
            checkValidation()
        }

    @get:Bindable
    override var receiverCurrency: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.receiverCurrency)
        }

    @get:Bindable
    override var receiverCurrencyAmount: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.receiverCurrencyAmount)
        }

    @get:Bindable
    override var receiverCurrencyAmountFxRate: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.receiverCurrencyAmountFxRate)

        }

    @get:Bindable
    override var fromFxRate: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fromFxRate)
        }
    @get:Bindable
    override var fromFxRateCurrency: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fromFxRateCurrency)
        }
    @get:Bindable
    override var toFxRate: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toFxRate)
        }
    @get:Bindable
    override var toFxRateCurrency: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toFxRateCurrency)
        }


//
//    @get:Bindable
//    override var reasonList: ArrayList<InternationalFundsTransferReasonList.ReasonList>? =
//        ArrayList()
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.reasonList)
//        }



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
    override var senderCurrency: String? = ""
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
    override var beneficiaryAmount: String = ""
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

    private fun checkValidation() {

        if (!receiverCurrencyAmountFxRate.isNullOrEmpty()) {
            fxRateAmount?.let {
                if (it?.isEmpty()) {
                    receiverCurrencyAmount = ""
                }
            }
            val amount =
                receiverCurrencyAmountFxRate?.let { fxRateAmount?.toDouble()?.times(it.toDouble()) }
            receiverCurrencyAmount = amount.toString()

        }

    }

}