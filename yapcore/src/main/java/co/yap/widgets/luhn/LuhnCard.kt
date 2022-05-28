package co.yap.widgets.luhn

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LuhnCard(
    val pan: String? = null,
    val cardName: String? = null,
    val expDate: String? = null,
    val cvv: String? = null,
    val pin: String? = null, val expMonth: Int? = null, val expYear: Int? = null
) : Parcelable