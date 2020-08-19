package co.yap.multicurrency.currencyexchange.currencyexchangeinput

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.multicurrency.R

class CurrencyExchangeInputFragmentDirections private constructor() {
    companion object {
        fun actionCurrencyExchangeInputFragmentToCurrencyExchangeConfirmationFragment():
                NavDirections =
                ActionOnlyNavDirections(R.id.action_currencyExchangeInputFragment_to_currencyExchangeConfirmationFragment)
    }
}
