package co.yap.modules.location.tax

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaxReasonModel(
    @SerializedName("data")
    var reasons: ArrayList<String> = arrayListOf()
) : ApiResponse(), Parcelable

//"errors": null,
//"data": [
//"The country does not issue a TIN",
//"Unable to obtain TIN",
//"No TIN required"
//]