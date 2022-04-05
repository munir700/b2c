package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText


class TopupAmountViewModel(application: Application) :
    AddMoneyBaseViewModel<ITopupAmount.State>(application),
    ITopupAmount.ViewModel  {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ITopupAmount.State = TopupAmountState()

    override fun handleClickEvent(id: Int) {
        clickEvent.setValue(id)
    }

    override fun denominationAmountValidator(amount: String) {
        val currency = Utils.getFormattedCurrencyWithoutComma(
            amount.replace(
                if (amount.contains("+")) "+" else "-",
                ""
            )
        )
        if (!state.enteredTopUpAmount.value.equals(currency)) {
            state.enteredTopUpAmount.value = currency
            state.valid.value = true
           /* >>> to be used when user remaining balance is known via api
             state.valid.value =
                            state.enteredTopUpAmount.value?.getValueWithoutComa().parseToDouble() <= state.remainingAccumulative.get()?:0.0*/
        } else {
            state.enteredTopUpAmount.value = ""
            state.valid.value = true
        }
    }

    fun setAvailableBalance() {
        state.availableBalance.value = context.resources.getText(
            getString(Strings.common_display_text_available_balance),
            context.color(  R.color.colorPrimaryDark,
                "2000".toFormattedCurrency()         ////Hard coded currency used,
            ))                                       ///to be replaced by the model currency fetched from api
    }

}