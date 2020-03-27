package co.yap.sendMoney.fundtransfer.models

data class TransferFundData(
    var position: Int? = 0,
    var noteValue: String? = null,
    var transferAmount: String? = "",
    var transferFee: String? = "",
    var cutOffTimeMsg: String? = null,
    var referenceNumber: String? = null,
    var purposeCode: String? = null,
    var transferReason: String? = null,
    var otpAction: String? = null,
    var productCode: String? = null,
//specific international transfer data
    var sourceCurrency: String? = null,
    var sourceAmount: String? = null,
    var destinationCurrency: String? = null,
    var destinationAmount: String? = null,
    var toFxRate: String? = null,
    var fromFxRate: String? = null,
    var rate: String? = null

)