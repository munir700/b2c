package co.yap.sendmoney.fundtransfer.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R

class CashTransferConfirmationFragmentDirections private constructor() {
    companion object {
        fun actionCashTransferConfirmationFragmentToTransferSuccessFragment2(): NavDirections =
                ActionOnlyNavDirections(R.id.action_cashTransferConfirmationFragment_to_transferSuccessFragment2)
    }
}
