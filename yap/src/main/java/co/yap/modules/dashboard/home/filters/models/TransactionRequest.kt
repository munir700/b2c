package co.yap.modules.dashboard.home.filters.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionRequest(
    var number: Int = 0,
    var size: Int = 0,
    var amountStartRange: Double? = 0.0,
    var amountEndRange: Double? = 0.0,
    var txnType: String? = null,
    var title: String? = null,
    var totalAppliedFilter: Int = 0
) : Parcelable
