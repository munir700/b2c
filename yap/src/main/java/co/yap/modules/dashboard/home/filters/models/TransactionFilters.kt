package co.yap.modules.dashboard.home.filters.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionFilters(
    var amountStartRange: Double? = null,
    var amountEndRange: Double? = null,
    var incomingTxn: Boolean? = false,
    var outgoingTxn: Boolean? = false,
    var totalAppliedFilter: Int = 0
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (other is TransactionFilters) {
            return other.amountStartRange == amountStartRange
                    && other.amountEndRange == amountEndRange
                    && other.incomingTxn == incomingTxn
                    && other.outgoingTxn == outgoingTxn
        }
        return false
    }
}
