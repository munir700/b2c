package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.states

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseState
import com.google.android.material.snackbar.Snackbar
import java.util.*

class FundActionsState(application: Application) : BaseState(), IFundActions.State {

    var context: Context = application.applicationContext
    @get:Bindable
    override var cardNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardNumber)
        }

    @get:Bindable
    override var toolBarHeader: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolBarHeader)
        }

    @get:Bindable
    override var cardName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardName)
        }

    @get:Bindable
    override var enterAmountHeading: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.enterAmountHeading)
        }

    @get:Bindable
    override var currencyType: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyType)
        }

    @get:Bindable
    override var denominationFirstAmount: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.denominationFirstAmount)
        }

    @get:Bindable
    override var denominationSecondAmount: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.denominationSecondAmount)
        }

    @get:Bindable
    override var denominationThirdAmount: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.denominationThirdAmount)
        }

    @get:Bindable
    override var availableBalanceGuide: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalanceGuide)
        }

    @get:Bindable
    override var availableBalance: String = "500"
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalance)
        }

    @get:Bindable
    override var buttonTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonTitle)
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
    override var maxLimit: Double = 0.00
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxLimit)
        }

    @get:Bindable
    override var minLimit: Double = 0.00
        set(value) {
            field = value
            notifyPropertyChanged(BR.minLimit)
        }

    @get:Bindable
    override var amountBackground: Drawable? =
        context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds,null)
        set(value) {
            field = value
            notifyPropertyChanged(BR.amountBackground)
        }

    @get:Bindable
    override var errorDescription: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorDescription)
        }

    @get:Bindable
    override var availableBalanceText: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalanceText)
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
                    availableBalance
                )
                return errorDescription
            } else if (amount?.toDouble()!! > maxLimit) {
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds_error, null)

                errorDescription = Translator.getString(
                    context,
                    Strings.screen_add_funds_display_text_max_limit_error,
                    currencyType,
                    availableBalance
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
            valid = amount?.toDouble()!! > minLimit
           // if (amount?.toDouble()!! < availableBalance.toDouble() || amount?.toDouble()!! < maxLimit) {
                amountBackground =
                    context.resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
            //}
        }
    }
}