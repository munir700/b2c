package co.yap.sendMoney.addbeneficiary.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R

class AddBeneficiaryInternationlTransferFragmentDirections private constructor() {
    companion object {
        fun actionAddBeneficiaryFragmentToAddBankDetailsFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)
    }
}
