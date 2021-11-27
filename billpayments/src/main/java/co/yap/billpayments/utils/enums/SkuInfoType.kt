package co.yap.billpayments.utils.enums

sealed class SkuInfoType {
    data class Airtime(var airtime: String = "Airtime") : SkuInfoType()
    data class Data(var data: String = "Data") : SkuInfoType()
}