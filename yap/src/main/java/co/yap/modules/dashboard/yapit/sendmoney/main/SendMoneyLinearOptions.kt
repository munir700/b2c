package co.yap.modules.dashboard.yapit.sendmoney.main

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class SendMoneyLinearOptions(
    var name: String,
    var image: String,
    var type: SendMoneyCategoryType = SendMoneyCategoryType.None,
    var description: String,
    var isFlag : Boolean
) : Parcelable

enum class SendMoneyCategoryType {
    SendMoneyToYAPContacts,SendMoneyQRCode, SendMoneyToLocalBank, SendMoneyToHomeCountry,  SendMoneyToInternational, None
}