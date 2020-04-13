package co.yap.sendmoney.addbeneficiary.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R

class AddBankDetailsFragmentDirections private constructor() {
    companion object {
        fun actionAddBankDetailsFragmentToBeneficiaryAccountDetailsFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_addBankDetailsFragment_to_beneficiaryAccountDetailsFragment)
    }
}
