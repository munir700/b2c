package co.yap.countryutils.country

import co.yap.networking.models.ApiResponse

data class CountriesResponseDTO(
    var data: List<Country>,
    var errors: Any?
) : ApiResponse()
