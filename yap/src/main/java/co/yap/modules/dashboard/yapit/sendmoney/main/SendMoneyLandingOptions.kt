package co.yap.modules.dashboard.yapit.sendmoney.main

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class SendMoneyLandingOptions(
    var name: String,
    val image: Int,
    val showFlag : Boolean,
    var flag : Int? = null
) : Parcelable