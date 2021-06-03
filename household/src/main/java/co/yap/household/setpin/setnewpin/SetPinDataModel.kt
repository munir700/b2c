package co.yap.household.setpin.setnewpin

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class SetPinDataModel(
    var screenType: String?="",
    var pinCode: String?="",
    var toolBarTitle: String? = "",
    var setPinTitle: String? = "",
    var termsAndConditionVisibility: Boolean? = false,
    var buttonTitle: String? = "",
    var forgotPassCodeVisibility: Boolean? = false
) : Parcelable