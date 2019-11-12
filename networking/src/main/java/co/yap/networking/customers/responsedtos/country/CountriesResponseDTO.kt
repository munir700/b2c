package co.yap.networking.customers.responsedtos.country

import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.models.ApiResponse

data class CountriesResponseDTO(
    val data: List<Contact>,
    var errors: Any?
) : ApiResponse()
