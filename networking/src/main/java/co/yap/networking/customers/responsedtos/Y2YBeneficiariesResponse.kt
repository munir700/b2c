package co.yap.networking.customers.responsedtos

import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.models.ApiResponse

data class Y2YBeneficiariesResponse(
    val data: List<Contact>,
    var errors: Any?
) : ApiResponse()