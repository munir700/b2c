package co.yap.networking.customers.responsedtos

import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.models.ApiResponse

data class CreateBeneficiaryResponse(val data: TopUpCard?) : ApiResponse()