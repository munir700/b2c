package co.yap.sendmoney.addbeneficiary.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R

class BeneficiaryAccountDetailsFragmentDirections private constructor() {
    companion object {
        fun actionBeneficiaryAccountDetailsFragmentToBeneficiaryOverviewFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_beneficiaryAccountDetailsFragment_to_beneficiaryOverviewFragment)
    }
}
