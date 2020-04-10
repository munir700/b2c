package co.yap.sendMoney.addbeneficiary.fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R
import kotlin.Boolean
import kotlin.Int

class TransferTypeFragmentDirections private constructor() {
    private data class
            ActionTransferTypeFragmentToAddBeneficiaryForDomesticTransferFragment(val successOtp:
            Boolean = false) : NavDirections {
        override fun getActionId(): Int =
                R.id.action_transferTypeFragment_to_addBeneficiaryForDomesticTransferFragment

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putBoolean("successOtp", this.successOtp)
            return result
        }
    }

    companion object {
        fun actionTransferTypeFragmentToSelectCountryFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_transferTypeFragment_to_selectCountryFragment)

        fun actionTransferTypeFragmentToAddBeneficiaryFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_transferTypeFragment_to_addBeneficiaryFragment)

        fun actionTransferTypeFragmentToAddBeneficiaryForDomesticTransferFragment(successOtp: Boolean
                = false): NavDirections =
                ActionTransferTypeFragmentToAddBeneficiaryForDomesticTransferFragment(successOtp)

        fun actionTransferTypeFragmentToAddBeneficiaryForCashFlowFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_transferTypeFragment_to_addBeneficiaryForCashFlowFragment)
    }
}
