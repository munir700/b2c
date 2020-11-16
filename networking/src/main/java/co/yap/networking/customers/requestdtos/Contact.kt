package co.yap.networking.customers.requestdtos

import android.os.Parcelable
import co.yap.networking.customers.responsedtos.sendmoney.SearchBeneficiaryData
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("countryCode")
    var countryCode: String? = "",
    @SerializedName("mobileNo")
    var mobileNo: String? = "",
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("beneficiaryPictureUrl")
    var beneficiaryPictureUrl: String? = "",
    @SerializedName("yapUser")
    var yapUser: Boolean? = false,
    @SerializedName("accountDetailList")
    val accountDetailList: List<Data>? = null
) : ApiResponse(), SearchBeneficiaryData, Parcelable {
    override var searchableTitle: String?
        get() = title
        set(value) {}
    override var searchableSubTitle: String?
        get() = mobileNo
        set(value) {}
    override var searchableIcon: String?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var searchableIndicator: String?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var searchableTransferType: String?
        get() = if (yapUser == true) "Y2Y" else ""
        set(value) {}

    @Parcelize
    data class Data(
        @SerializedName("accountNo")
        val accountNo: String? = "",
        @SerializedName("accountType")
        val accountType: String? = "",
        @SerializedName("accountUuid")
        val accountUuid: String? = ""
    ) : ApiResponse(), Parcelable
}