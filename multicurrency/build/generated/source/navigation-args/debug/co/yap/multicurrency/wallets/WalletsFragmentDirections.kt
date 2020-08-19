package co.yap.multicurrency.wallets

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.multicurrency.R

class WalletsFragmentDirections private constructor() {
    companion object {
        fun actionWalletsFragmentToWalletDetailsFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_walletsFragment_to_walletDetailsFragment)
    }
}
