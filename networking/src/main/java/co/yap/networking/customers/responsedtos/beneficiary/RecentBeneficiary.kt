package co.yap.networking.customers.responsedtos.beneficiary

import co.yap.networking.models.ApiResponse

data class RecentBeneficiary(
    val accountUuid: String,
    val beneficiaryAccountType: String,
    val beneficiaryPictureUrl: String,
    val beneficiaryType: String,
    val beneficiaryUuid: String,
    val id: Int,
    val lastUsedDate: String,
    val mobileNo: String,
    val title: String
) : ApiResponse()