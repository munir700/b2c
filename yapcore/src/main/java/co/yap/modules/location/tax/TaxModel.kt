package co.yap.modules.location.tax

import android.os.Parcelable
import androidx.databinding.ObservableField
import co.yap.modules.location.POBCountry
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaxModel(
    var countries: ArrayList<POBCountry> = arrayListOf(),
    var reasons: ArrayList<String> = arrayListOf(),
    var selectedReason: ObservableField<String> = ObservableField("Yes"),
    var options: ArrayList<String> = arrayListOf("Yes", "No"),
    var taxRowNumber: ObservableField<Boolean>,
    var canAddMore: ObservableField<Boolean>
) : Parcelable