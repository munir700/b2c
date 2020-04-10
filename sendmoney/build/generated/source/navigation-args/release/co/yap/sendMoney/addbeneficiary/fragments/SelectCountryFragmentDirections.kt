package co.yap.sendMoney.addbeneficiary.fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import co.yap.sendmoney.R
import kotlin.Boolean
import kotlin.Int

class SelectCountryFragmentDirections private constructor() {
    private data class ActionSelectCountryFragmentToDomesticFragment(val successOtp: Boolean =
            false) : NavDirections {
        override fun getActionId(): Int = R.id.action_selectCountryFragment_to_DomesticFragment

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putBoolean("successOtp", this.successOtp)
            return result
        }
    }

    companion object {
        fun actionSelectCountryFragmentToTransferTypeFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_selectCountryFragment_to_transferTypeFragment)

        fun actionSelectCountryFragmentToAddBeneficiaryFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_selectCountryFragment_to_addBeneficiaryFragment)

        fun actionSelectCountryFragmentToDomesticFragment(successOtp: Boolean = false):
                NavDirections = ActionSelectCountryFragmentToDomesticFragment(successOtp)
    }
}
