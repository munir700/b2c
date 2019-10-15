package co.yap.modules.dashboard.sendmoney.utils

data class CountryModel(
    var `data`: List<Data>,
    var errors: Any?
) {
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