package co.yap.networking.customers.responsedtos

data class SectionedCountryData(
    var active: Boolean,
    var id: Int,
    var isoCountryCode2Digit: String,
    var isoCountryCode3Digit: String,
    var name: String,
    var signUpAllowed: Boolean
)