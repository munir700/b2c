package co.yap.modules.subaccounts.householdsetpin.setNewpin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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