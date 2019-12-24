package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.Utils

class CashTransferState(application: Application) : BaseState(), ICashTransfer.State {

    val context: Context = application.applicationContext

    @get:Bindable
    override var fullName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)
        }


    @get:Bindable
    override var amountBackground: Drawable? =
        context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
        set(value) {
            field = value
            notifyPropertyChanged(BR.amountBackground)
        }
    @get:Bindable
    override var feeAmountString: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.feeAmountString)
        }
    @get:Bindable
    override var feeAmountSpannableString: SpannableStringBuilder? = SpannableStringBuilder("")
        set(value) {
            field = value
            notifyPropertyChanged(BR.feeAmountSpannableString)
        }

    @get:Bindable
    override var amount: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.amount)
            clearError()

            if (amount.isNotEmpty()) {
                setSpannableFee(findFee(amount.toDouble()).toString())
            } else {
                setSpannableFee("0.0")
            }

        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }
    @get:Bindable
    override var minLimit: Double = 0.01
        set(value) {
            field = value
            notifyPropertyChanged(BR.minLimit)
        }
    @get:Bindable
    override var position: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }

    @get:Bindable
    override var availableBalance: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalance)
        }

    @get:Bindable
    override var errorDescription: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorDescription)
        }
    @get:Bindable
    override var currencyType: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyType)
        }

    @get:Bindable
    override var maxLimit: Double = 999999.00
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxLimit)
        }

    @get:Bindable
    override var availableBalanceText: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalanceText)
        }

    @get:Bindable
    override var availableBalanceGuide: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalanceGuide)
        }

    @get:Bindable
    override var noteValue: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.noteValue)
        }

    @get:Bindable
    override var imageUrl: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUrl)
        }
    @get:Bindable
    override var feeStringVisibility: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.feeStringVisibility)
        }
    @get:Bindable
    override var ibanNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.ibanNumber)
        }
    @get:Bindable
    override var ibanVisibility: Boolean? = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.ibanVisibility)
        }
    @get:Bindable
    override var beneficiaryCountry: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryCountry)
        }
    @get:Bindable
    override var referenceNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.referenceNumber)
        }
    @get:Bindable
    override var reasonsVisibility: Boolean? = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.reasonsVisibility)
        }
    @get:Bindable
    override var produceCode: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.produceCode)
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
    override var transferFee: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferFee)
        }

    @get:Bindable
    override var transferFeeSpannable: SpannableStringBuilder? = SpannableStringBuilder("")
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferFeeSpannable)
        }

    @get:Bindable
    override var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> =
        ArrayList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.listItemRemittanceFee)
        }

    @get:Bindable
    override var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList> =
        ArrayList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.transactionData)
        }


    @get:Bindable
    override val populateSpinnerData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()


    fun checkValidity(): String {
        if (amount != "") {
            if (amount.isNotEmpty() && !availableBalance.isNullOrEmpty()) {
                if (amount.toDouble() > availableBalance!!.toDouble()) {
                    amountBackground =
                        context.resources.getDrawable(
                            co.yap.yapcore.R.drawable.bg_funds_error,
                            null
                        )
                    errorDescription = Translator.getString(
                        context,
                        Strings.screen_y2y_funds_transfer_display_text_error_exceeding_amount
                    )
                    return errorDescription
                } else {
                    amountBackground =
                        context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
                }
            }
        }
        return ""
    }


    private fun clearError() {
        if (amount != "") {
            if (amount != ".") {
                valid = amount.toDouble() >= minLimit
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
            }
        } else if (amount == "") {
            valid = false
        }
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

    private fun setSpannableFee(totalAmount: String) {
        transferFee =
            Translator.getString(
                context,
                Strings.screen_cash_pickup_funds_display_text_fee
            ).format(
                "AED",
                Utils.getFormattedCurrency(totalAmount)
            )
        feeAmountSpannableString =
            Utils.getSppnableStringForAmount(
                context,
                transferFee,
                "AED",
                Utils.getFormattedCurrencyWithoutComma(totalAmount)
            )


    }

}