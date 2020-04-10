package co.yap.sendMoney.addbeneficiary.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R

class BeneficiaryOverviewFragmentDirections private constructor() {
    companion object {
        fun actionBeneficiaryOverviewFragmentToBeneficiaryAccountDetailsFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_beneficiaryOverviewFragment_to_beneficiaryAccountDetailsFragment)

        fun actionBeneficiaryOverviewFragmentToTransferSuccessFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_beneficiaryOverviewFragment_to_transferSuccessFragment)
    }
}
