package co.yap.modules.yapit.y2ytransfer.transfer.states

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.yapit.y2ytransfer.transfer.interfaces.IY2YTransfer
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.Utils

class Y2YTransferState(application: Application) : BaseState() , IY2YTransfer.State{

    val context:Context=application.applicationContext
   @get:Bindable
    override var amountBackground: Drawable? =
        context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
        set(value) {
            field = value
            notifyPropertyChanged(BR.amountBackground)
        }

    @get:Bindable
    override var amount: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.amount)
            clearError()
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }
    @get:Bindable
    override var minLimit: Double = 0.00
        set(value) {
            field = value
            notifyPropertyChanged(BR.minLimit)
        }

    @get:Bindable
    override var availableBalance: String = "500"
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
    override var availableBalanceText: String=""
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


    fun checkValidity(): String {
        if (amount != "") {
            if (amount?.toDouble()!! > availableBalance.toDouble()) {
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds_error, null)
                    errorDescription = Translator.getString(
                        context,
                        Strings.screen_add_funds_display_text_available_balance_error,
                        currencyType,
                        Utils.getFormattedCurrency(availableBalance)
                    )

                return errorDescription
            } else if (amount?.toDouble()!! > maxLimit) {
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds_error, null)

                errorDescription = Translator.getString(
                    context,
                    Strings.screen_add_funds_display_text_max_limit_error,
                    currencyType,
                    Utils.getFormattedCurrency(maxLimit.toString())
                )
                return errorDescription

            } else {
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
            }
        }
        return ""
    }


    private fun clearError() {
        if (amount != "") {
            if (amount != ".") {
                valid = amount?.toDouble()!! >= minLimit
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
            }
        } else if (amount == "") {
            valid = false
        }
    }

}