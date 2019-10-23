package co.yap.networking.customers.requestdtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var title: String?,
    var countryCode: String?,
    var mobileNo: String?,
    var email: String?,
    var yapUser: Boolean?
) : Parcelable