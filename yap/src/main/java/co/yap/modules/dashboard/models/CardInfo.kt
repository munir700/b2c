package co.yap.modules.dashboard.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class CardInfo(
    var cardId: String,
    var cardColor: String,
    var cardNickname: String,
    var cardNo: String,
    var cardExpiry: String,
    var cardType: String
) : Parcelable
