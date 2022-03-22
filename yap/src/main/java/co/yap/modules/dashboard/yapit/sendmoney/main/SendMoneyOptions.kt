package co.yap.modules.dashboard.yapit.sendmoney.main

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class SendMoneyOptions(
    var name: String,
    val image: Int,
    val showFlag: Boolean? = false,
    var flag: Int? = null,
    var type: SendMoneyType = SendMoneyType.none,
    var description: String
) : Parcelable

enum class SendMoneyType {
    sendMoneyToYAPContacts, sendMoneyToLocalBank, sendMoneyToHomeCountry, sendMoneyQRCode, sendMoneyToInternational, none
}