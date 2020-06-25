package co.yap.yapcore.helpers.extentions

import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode

fun Beneficiary?.getProductCode(): String {
    this?.let { beneficiary ->
        return when (beneficiary.beneficiaryType) {
            SendMoneyBeneficiaryType.CASHPAYOUT.type -> TransactionProductCode.CASH_PAYOUT.pCode
            SendMoneyBeneficiaryType.UAEFTS.type -> TransactionProductCode.UAEFTS.pCode
            SendMoneyBeneficiaryType.DOMESTIC.type -> TransactionProductCode.DOMESTIC.pCode
            SendMoneyBeneficiaryType.RMT.type -> TransactionProductCode.RMT.pCode
            SendMoneyBeneficiaryType.SWIFT.type -> TransactionProductCode.SWIFT.pCode
            else -> ""
        }
    } ?: return ""
}