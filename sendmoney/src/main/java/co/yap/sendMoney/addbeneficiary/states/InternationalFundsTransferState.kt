package co.yap.sendMoney.addbeneficiary.states

import android.app.Application
import android.text.SpannableStringBuilder
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseState
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import java.util.*
import kotlin.collections.ArrayList

class InternationalFundsTransferState(val application: Application) : BaseState(),
    IInternationalFundsTransfer.State {


    val context = application.applicationContext

    @get:Bindable
    override var srRate: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.srRate)
        }

    @get:Bindable
    override var transferFee: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferFee)

        }

    override var totalTransferAmount: ObservableField<Double> = ObservableField(0.0)

    @get:Bindable
    override var errorDescription: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorDescription)
        }
    @get:Bindable
    override var availableBalanceString: CharSequence? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalanceString)
        }

    @get:Bindable
    override var reasonTransferValue: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.reasonTransferValue)
        }
    @get:Bindable
    override var reasonTransferCode: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.reasonTransferCode)
        }

    @get:Bindable
    override var transferFeeSpannable: SpannableStringBuilder? = SpannableStringBuilder("")
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferFeeSpannable)
        }
    @get:Bindable
    override var fxRateAmount: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fxRateAmount)
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
    override var firstName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }
    @get:Bindable
    override var totalAmount: Double? = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalAmount)
        }
    @get:Bindable
    override var internationalFee: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.internationalFee)
        }
    @get:Bindable
    override var formattedFee: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.formattedFee)
        }
    @get:Bindable
    override var referenceNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.referenceNumber)
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
    override var toFxRate: String? = "0.0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.toFxRate)
        }

    @get:Bindable
    override var rate: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.rate)
            checkValidation()
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
    override var beneficiaryPicture: String = ""
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
    override var beneficiaryName: String = ""
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
    override var beneficiaryCountry: String? = ""
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
    override var beneficiaryId: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryId)
        }
    @get:Bindable
    override var position: Int? = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
    @get:Bindable
    override var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> =
        ArrayList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.listItemRemittanceFee)
        }


    @get:Bindable
    override var transferFeeAmount: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferFeeAmount)
        }
    @get:Bindable
    override var maxLimit: Double? = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxLimit)
        }
    @get:Bindable
    override var minLimit: Double? = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.minLimit)
        }
    @get:Bindable
    override var transactionNote: String? = null
        set(value) {
            field = if (value.isNullOrBlank()) null else value
            notifyPropertyChanged(BR.transactionNote)
        }
    @get:Bindable
    override var feeType: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.feeType)
        }

    @get:Bindable
    override var beneficiary: Beneficiary? = Beneficiary()
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiary)
        }

    fun validate() {
        if (!senderAmount.isNullOrEmpty()) {
            if (senderAmount.toInt() >= 1 && !beneficiaryAmount.isNullOrEmpty()/* &&  reason must be selected as well */) {
                valid = true
            }
        }
    }

    override fun checkValidation() {
        if (rate.isNullOrBlank()) {
            valid = false
            return
        }

        if (!receiverCurrencyAmountFxRate.isNullOrEmpty()) {
            if (fxRateAmount.isNullOrEmpty() && feeType == Constants.FEE_TYPE_TIER) {
                setSpanable(0.0)
                receiverCurrencyAmount = "0.00"
                return
            } else {
                fxRateAmount?.let {
                    // if (feeType == Constants.FEE_TYPE_TIER) {
                    receiverCurrencyAmount =
                        if (it.isEmpty() && feeType == Constants.FEE_TYPE_TIER) {
                            setSpanable(0.0)
                            ""
                        } else {
                            var amount =
                                receiverCurrencyAmountFxRate?.let {

                                    if (!fxRateAmount.isNullOrEmpty() && fxRateAmount != ".")
                                        fxRateAmount?.toDouble()?.times(it.toDouble())
                                    else {
                                        receiverCurrencyAmount = "0.00"
                                        return
                                    }
                                }
                            if (feeType == Constants.FEE_TYPE_TIER) {
                                setSpanable(amount ?: 0.0)
                            }
                            val amountFxRate = amount
                            val receiveFxRate = rate?.toDoubleOrNull()
                            val result = amountFxRate?.times(receiveFxRate ?: 0.0)
                            receiverCurrencyAmount =
                                String.format(
                                    Locale.getDefault(),
                                    "%.02f",
                                    result
                                ) //result.toString()
                            receiverCurrencyAmount.toString()
                        }
                    //  }
                }
            }
        }

    }

    fun setSpanable(amount: Double) {
        transferFee =
            Translator.getString(
                context,
                Strings.screen_international_funds_transfer_display_text_fee
            ).format(
                "AED",
                findFee(amount).toString().toFormattedCurrency()
            )
        formattedFee = "${"AED"} ${findFee(amount).toString().toFormattedCurrency()}"
        internationalFee = "${"AED"} ${findFee(amount)}"

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
        totalAmount = 0.0
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
        transferFeeAmount = totalAmount ?: 0.0
        return totalAmount ?: 0.0

    }

    override fun clearError() {
        if (fxRateAmount != "") {
            if (fxRateAmount != "." && fxRateAmount?.toDouble() ?: 0.0 > 0.0) {
                valid = true
//                valid = amount.toDouble() >= minLimit
/*                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)*/
            }
        } else if (fxRateAmount == "") {
            valid = false
            cancelAllSnackBar()
        }
    }

}
