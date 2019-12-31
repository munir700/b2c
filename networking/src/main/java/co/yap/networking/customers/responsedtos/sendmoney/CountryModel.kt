package co.yap.networking.customers.responsedtos.sendmoney

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("data")
    var data: List<Data>? = arrayListOf(),
    @SerializedName("errors")
    var errors: Any?
) : ApiResponse() {
    data class Data(
        @SerializedName("active")
        var active: Boolean? = false,
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("isoCountryCode2Digit")
        var isoCountryCode2Digit: String? = "",
        @SerializedName("isoCountryCode3Digit")
        var isoCountryCode3Digit: String? = "",
        @SerializedName("isoNum")
        var isoNum: String? = "",
        @SerializedName("name")
        var name: String? = "",
        @SerializedName("signUpAllowed")
        var signUpAllowed: Boolean? = false,
        @SerializedName("currencyList")
        var currencyList: List<Currency>? = arrayListOf()
    ) {
        data class Currency(
            @SerializedName("code")
            var code: String? = "",
            @SerializedName("default")
            var default: Boolean? = false,
            @SerializedName("name")
            var name: String? = "",
            @SerializedName("active")
            var active: Boolean? = false,
            @SerializedName("cashPickUp")
            var cashPickUp: Boolean? = false,
            @SerializedName("rmtCountry")
            var rmtCountry: Boolean? = false
        )
    }
}
