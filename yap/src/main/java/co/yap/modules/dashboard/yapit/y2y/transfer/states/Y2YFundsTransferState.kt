package co.yap.modules.dashboard.yapit.y2y.transfer.states

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.yapit.y2y.transfer.interfaces.IY2YFundsTransfer
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.extentions.toFormattedAmountWithCurrency
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

class Y2YFundsTransferState(application: Application) : BaseState(), IY2YFundsTransfer.State {

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
    override var noteValue: String = ""
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
    override var transferFee: CharSequence? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferFee)
        }

    fun checkValidity(amount: String): String {
        if (amount.isNotEmpty() && !availableBalance.isNullOrEmpty() && amount.toDoubleOrNull() ?: 0.0 >= minLimit) {
            if (amount.toDoubleOrNull() ?: 0.0 > availableBalance?.toDoubleOrNull() ?: 0.0) {
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds_error, null)
                errorDescription = Translator.getString(
                    context,
                    Strings.common_display_text_available_balance_error
                ).format(availableBalance?.toFormattedAmountWithCurrency())
                valid = false
                return errorDescription
            } else {
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
                valid = true
            }
        } else
            valid = false

        return ""
    }
}