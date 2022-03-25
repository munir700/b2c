package co.yap.modules.dashboard.yapit.sendmoney.main

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class SendMoneyLinearOptions(
    var name: String,
    var image: Int,
    var type: SendMoneyCategoryType = SendMoneyCategoryType.None,
    var description: String
) : Parcelable

enum class SendMoneyCategoryType {
    SendMoneyToYAPContacts, SendMoneyToLocalBank, SendMoneyToHomeCountry, SendMoneyQRCode, SendMoneyToInternational, None
}