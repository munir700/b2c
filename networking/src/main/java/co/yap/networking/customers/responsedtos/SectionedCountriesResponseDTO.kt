package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse

data class SectionedCountriesResponseDTO(
    var data: List<SectionedCountryData>,
    var errors: Any?
) : ApiResponse()