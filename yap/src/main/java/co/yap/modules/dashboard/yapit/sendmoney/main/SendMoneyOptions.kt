package co.yap.modules.dashboard.yapit.sendmoney.main

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Deprecated(message = "Deprecating this class as it was customized with the flag at top left corner, we are no longer in need of that functionality",
    replaceWith = ReplaceWith("SendMoneyLinearOptions.kt"))
data class SendMoneyOptions(
    var name: String,
    val image: Int,
    val showFlag: Boolean,
    var flag: Int? = null,
    var type: SendMoneyType = SendMoneyType.none
) : Parcelable

enum class SendMoneyType {
    sendMoneyToYAPContacts, sendMoneyToLocalBank, sendMoneyToHomeCountry, sendMoneyQRCode, sendMoneyToInternational, none
}