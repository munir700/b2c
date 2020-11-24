package co.yap.modules.dashboard.cards.addpaymentcard.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class VirtualCardModel (
val cardName : String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cardName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VirtualCardModel> {
        override fun createFromParcel(parcel: Parcel): VirtualCardModel {
            return VirtualCardModel(parcel)
        }

        override fun newArray(size: Int): Array<VirtualCardModel?> {
            return arrayOfNulls(size)
        }
    }
}