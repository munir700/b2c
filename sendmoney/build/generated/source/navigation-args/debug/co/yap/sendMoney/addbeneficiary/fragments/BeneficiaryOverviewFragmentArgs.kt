package co.yap.sendMoney.addbeneficiary.fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavArgs
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

data class BeneficiaryOverviewFragmentArgs(val beneficiary: Beneficiary) : NavArgs {
    @Suppress("CAST_NEVER_SUCCEEDS")
    fun toBundle(): Bundle {
        val result = Bundle()
        if (Parcelable::class.java.isAssignableFrom(Beneficiary::class.java)) {
            result.putParcelable("beneficiary", this.beneficiary as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(Beneficiary::class.java)) {
            result.putSerializable("beneficiary", this.beneficiary as Serializable)
        } else {
            throw UnsupportedOperationException(Beneficiary::class.java.name +
                    " must implement Parcelable or Serializable or must be an Enum.")
        }
        return result
    }

    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): BeneficiaryOverviewFragmentArgs {
            bundle.setClassLoader(BeneficiaryOverviewFragmentArgs::class.java.classLoader)
            val __beneficiary : Beneficiary?
            if (bundle.containsKey("beneficiary")) {
                if (Parcelable::class.java.isAssignableFrom(Beneficiary::class.java) ||
                        Serializable::class.java.isAssignableFrom(Beneficiary::class.java)) {
                    __beneficiary = bundle.get("beneficiary") as Beneficiary?
                } else {
                    throw UnsupportedOperationException(Beneficiary::class.java.name +
                            " must implement Parcelable or Serializable or must be an Enum.")
                }
                if (__beneficiary == null) {
                    throw IllegalArgumentException("Argument \"beneficiary\" is marked as non-null but was passed a null value.")
                }
            } else {
                throw IllegalArgumentException("Required argument \"beneficiary\" is missing and does not have an android:defaultValue")
            }
            return BeneficiaryOverviewFragmentArgs(__beneficiary)
        }
    }
}
