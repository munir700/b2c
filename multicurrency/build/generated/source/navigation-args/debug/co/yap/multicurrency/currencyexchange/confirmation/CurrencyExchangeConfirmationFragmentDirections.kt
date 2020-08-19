package co.yap.multicurrency.currencyexchange.confirmation

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.multicurrency.R

class CurrencyExchangeConfirmationFragmentDirections private constructor() {
    companion object {
        fun actionCurrencyExchangeConfirmationFragmentToCurrencyExchangeSuccessFragment():
                NavDirections =
                ActionOnlyNavDirections(R.id.action_currencyExchangeConfirmationFragment_to_currencyExchangeSuccessFragment)
    }
}
