package co.yap.networking.cards.responsedtos

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize


@Keep
@Parcelize
data class Card(
    val newPin: String?,

    val cardType: String,

    val uuid: String,

    val physical: Boolean,

    val active: Boolean,

    val cardName: String?,

    val status: String,

    var blocked: Boolean,

    val delivered: Boolean,

    val cardSerialNumber: String,

    val maskedCardNo: String,

    var atmAllowed: Boolean,

    var onlineBankingAllowed: Boolean,

    var retailPaymentAllowed: Boolean,

    var paymentAbroadAllowed: Boolean,

    val accountType: String,

    val expiryDate: String,

    val cardBalance: String,

    val cardScheme: String,

    val currentBalance: String,

    val availableBalance: String,

    val customerId: String,

    val accountNumber: String,

    val productCode: String,

    val pinCreated: Boolean
) : Parcelable