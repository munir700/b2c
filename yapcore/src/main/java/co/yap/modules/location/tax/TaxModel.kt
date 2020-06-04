package co.yap.modules.location.tax

import android.os.Parcelable
import co.yap.modules.location.POBCountry
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaxModel(
    val accountNumber: String? = null,
    var countries: ArrayList<POBCountry> = arrayListOf(),
    var reasons: ArrayList<String> = arrayListOf(),
    var options: ArrayList<String> = arrayListOf("Yes", "No"),
    val taxInfoNumber: Int = 0
) : Parcelable