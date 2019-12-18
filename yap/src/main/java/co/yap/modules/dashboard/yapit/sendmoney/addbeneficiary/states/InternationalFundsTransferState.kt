package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import android.app.Application
import android.text.SpannableStringBuilder
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.Utils

class InternationalFundsTransferState(val application: Application) : BaseState(),
    IInternationalFundsTransfer.State {

    val context = application.applicationContext
    @get:Bindable
    override var transferFee: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferFee)

        }
    @get:Bindable
    override var transferFeeSpannable: SpannableStringBuilder? = SpannableStringBuilder("")
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
    @get:Bindable
    override var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> =
        ArrayList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.listItemRemittanceFee)
        }

    fun validate() {
        if (!senderAmount.isNullOrEmpty() && !beneficiaryAmount.isNullOrEmpty()/* &&  reason must be selected as well */) {

            valid = true
        }
    }

    private fun checkValidation() {
        if (!receiverCurrencyAmountFxRate.isNullOrEmpty()) {
            fxRateAmount?.let {
                receiverCurrencyAmount = if (it.isEmpty()) {
                    setSpanable(0.0)
                    ""
                } else {
                    val amount =
                        receiverCurrencyAmountFxRate?.let {
                            fxRateAmount?.toDouble()?.times(it.toDouble())
                        }
                    setSpanable(amount ?: 0.0)
                    amount.toString()
                }
            }
        }
    }

    private fun setSpanable(amount: Double) {
        transferFee =
            Translator.getString(
                context,
                Strings.screen_international_funds_transfer_display_text_fee
            ).format(
                "AED",
                Utils.getFormattedCurrency(findFee(amount).toString())
            )

        notifyPropertyChanged(BR.transferFee)

        transferFeeSpannable =
            Utils.getSppnableStringForAmount(
                context,
                transferFee,
                "AED",
                Utils.getFormattedCurrencyWithoutComma(
                    findFee(amount).toString()
                )
            )
        notifyPropertyChanged(BR.transferFeeSpannable)
    }

    private fun findFee(
        value: Double
    ): Double {
        var totalAmount = 0.0
        val remittanceTierFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO>? =
            listItemRemittanceFee.filter { item -> item.amountFrom!! <= value && item.amountTo!! >= value }
        if (remittanceTierFee != null) {
            if (remittanceTierFee.isNotEmpty()) {
                val feeAmount = remittanceTierFee[0].feeAmount
                val feeAmountVAT = remittanceTierFee[0]?.vatAmount
                if (feeAmount != null) {
                    totalAmount = feeAmount + feeAmountVAT!!
                }
            }
        }
        return totalAmount
    }


}