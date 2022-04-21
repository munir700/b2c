package co.yap.modules.onboarding.models


sealed class CountryCode(val countryCode: String?){
    object UAE: CountryCode("+971")
    object GHANA: CountryCode("+233")
    object PAK: CountryCode("+92")
}
