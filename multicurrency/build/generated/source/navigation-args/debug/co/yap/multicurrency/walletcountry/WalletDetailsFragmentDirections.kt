package co.yap.multicurrency.walletcountry

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.multicurrency.R

class WalletDetailsFragmentDirections private constructor() {
    companion object {
        fun actionWalletDetailsFragmentToCurrencyExchangeInputFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_walletDetailsFragment_to_currencyExchangeInputFragment)
    }
}
