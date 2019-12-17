package co.yap.networking.customers.responsedtos.beneficiary

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class RecentBeneficiariesResponse(@SerializedName("dataList") val data: MutableList<RecentBeneficiary>) :
    ApiResponse()
