package co.yap.networking.customers.responsedtos.sendmoney

import co.yap.networking.models.ApiResponse

data class CountryModel(
    var `data`: List<Data>,
    var errors: Any?
): ApiResponse() {
    data class Data(
        var active: Boolean,
        var id: Int,
        var isoCountryCode2Digit: String,
        var isoCountryCode3Digit: String,
        var isoNum: String,
        var name: String,
        var signUpAllowed: Boolean
    )


}