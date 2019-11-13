package co.yap.networking.customers.responsedtos.beneficiary

import co.yap.networking.models.ApiResponse

data class TopUpBeneficiary(
    val id: String?,
    val logo: String?,
    val expiry: String?,
    val number: String?,
    val alias: String?,
    val color: String?
) : ApiResponse()
