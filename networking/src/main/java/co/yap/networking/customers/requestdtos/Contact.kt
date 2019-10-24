package co.yap.networking.customers.requestdtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var title: String?,
    var countryCode: String?,
    var mobileNo: String?,
    var email: String?,
    var beneficiaryPictureUrl: String?,
    var yapUser: Boolean?,
    val accountDetailList: List<Data>?
) : ApiResponse(), Parcelable {
    @Parcelize
    data class Data(
        val accountNo: String,
        val accountType: String,
        val accountUuid: String
    ) : ApiResponse(), Parcelable
}