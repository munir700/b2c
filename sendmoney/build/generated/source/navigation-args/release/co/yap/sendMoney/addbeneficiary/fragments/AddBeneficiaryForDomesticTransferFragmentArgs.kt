package co.yap.sendMoney.addbeneficiary.fragments

import android.os.Bundle
import androidx.navigation.NavArgs
import kotlin.Boolean
import kotlin.jvm.JvmStatic

data class AddBeneficiaryForDomesticTransferFragmentArgs(val successOtp: Boolean = false) : NavArgs
        {
    fun toBundle(): Bundle {
        val result = Bundle()
        result.putBoolean("successOtp", this.successOtp)
        return result
    }

    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): AddBeneficiaryForDomesticTransferFragmentArgs {
            bundle.setClassLoader(AddBeneficiaryForDomesticTransferFragmentArgs::class.java.classLoader)
            val __successOtp : Boolean
            if (bundle.containsKey("successOtp")) {
                __successOtp = bundle.getBoolean("successOtp")
            } else {
                __successOtp = false
            }
            return AddBeneficiaryForDomesticTransferFragmentArgs(__successOtp)
        }
    }
}
