package co.yap.networking.customers.responsedtos.country

import co.yap.networking.customers.responsedtos.country.utils.Country
import co.yap.networking.models.ApiResponse

data class CountriesResponseDTO(
    var data: List<Country>,
    var errors: Any?
) : ApiResponse()
