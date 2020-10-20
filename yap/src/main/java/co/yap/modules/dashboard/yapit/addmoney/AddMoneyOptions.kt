package co.yap.modules.dashboard.yapit.addmoney

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class AddMoneyOptions(
    val id: Int,
    var name: String,
    val image: Int,
    val bgColor: Int,
    val tint: Int
) : Parcelable