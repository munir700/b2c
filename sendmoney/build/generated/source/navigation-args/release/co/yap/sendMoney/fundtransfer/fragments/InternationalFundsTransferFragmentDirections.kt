package co.yap.sendMoney.fundtransfer.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R

class InternationalFundsTransferFragmentDirections private constructor() {
    companion object {
        fun actionInternationalFundsTransferFragmentToInternationalTransactionConfirmationFragment():
                NavDirections =
                ActionOnlyNavDirections(R.id.action_internationalFundsTransferFragment_to_internationalTransactionConfirmationFragment)
    }
}
