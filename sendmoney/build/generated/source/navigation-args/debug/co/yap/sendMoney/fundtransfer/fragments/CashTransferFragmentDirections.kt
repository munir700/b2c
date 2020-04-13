package co.yap.sendmoney.fundtransfer.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R

class CashTransferFragmentDirections private constructor() {
    companion object {
        fun actionCashTransferFragmentToTransferSuccessFragment2(): NavDirections =
                ActionOnlyNavDirections(R.id.action_cashTransferFragment_to_transferSuccessFragment2)

        fun actionCashTransferFragmentToInternationalFundsTransferFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_cashTransferFragment_to_internationalFundsTransferFragment)

        fun actionCashTransferFragmentToCashTransferConfirmationFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_cashTransferFragment_to_cashTransferConfirmationFragment)
    }
}
