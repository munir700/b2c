package co.yap.billpayments.payall.payallsuccess.adapter

import co.yap.networking.customers.models.BillerInputData

data class PaidBill(
    var billerID: String? = null,
    var skuID: String? = null,
    var billAmount: String? = null,
    var customerBillUuid: String? = null,
    var paymentInfo: String? = null,
    var billerCategory: String? = null,
    var billerType: String? = null,
    var billerName: String? = null,
    var billData: List<BillerInputData>? = null,
    var logo: String? = null,
    var paymentStatus: String? = null,
    var skuDiscription: String? = null

)