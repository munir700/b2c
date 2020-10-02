package co.yap.yapcore.helpers.extentions

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.SessionManager
import java.text.DecimalFormat

fun String?.toFormattedCurrency(showCurrency: Boolean = true, currency: String? = "AED"): String {
    return try {
        if (this?.isNotBlank() == true) {
            val formattedAmount = getDecimalFormatUpTo(
                selectedCurrencyDecimal = Utils.getConfiguredDecimals(currency ?: "AED"),
                amount = this
            )
            if (formattedAmount.isNotBlank()) {
                if (showCurrency)
                    "$currency $formattedAmount" else formattedAmount
            } else {
                ""
            }
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

private fun getDecimalFormatUpTo(selectedCurrencyDecimal: Int, amount: String): String {
    return try {
        val amountInDouble = java.lang.Double.parseDouble(amount)
        return when (selectedCurrencyDecimal) {
            0 -> {
                DecimalFormat("###,###,##0.00").format(amountInDouble)
            }
            1 -> {
                DecimalFormat("###,###,##0.0").format(amountInDouble)
            }
            2 -> {
                DecimalFormat("###,###,##0.00").format(amountInDouble)
            }
            3 -> {
                DecimalFormat("###,###,##0.000").format(amountInDouble)
            }
            4 -> {
                DecimalFormat("###,###,##0.0000").format(amountInDouble)
            }
            else -> {
                DecimalFormat("###,###,##0.00").format(amountInDouble)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String?.maskIbanNumber(): String {
    return this?.trim()?.let { iban ->
        return if (StringUtils.isValidIBAN(iban, iban.substring(0, 2))) {
            return if (PartnerBankStatus.ACTIVATED.status != SessionManager.user?.partnerBankStatus) {
                Utils.formateIbanString(iban)?.let { formattedIban ->
                    val numberToMasked =
                        formattedIban.substring(formattedIban.length - 7, formattedIban.length)
                    formattedIban.replace(numberToMasked, "*** ***")
                } ?: ""
            } else {
                Utils.formateIbanString(iban) ?: ""
            }
        } else {
            ""
        }
    } ?: ""
}

fun String?.maskAccountNumber(): String {
    return this?.trim()?.let { accountNumber ->
        return if (StringUtils.isValidAccountNumber(accountNumber)) {
            return if (PartnerBankStatus.ACTIVATED.status != SessionManager.user?.partnerBankStatus) {
                val numberToMasked =
                    accountNumber.substring(accountNumber.length - 6, accountNumber.length)
                accountNumber.replace(numberToMasked, "******")
            } else {
                accountNumber
            }
        } else {
            ""
        }
    } ?: ""
}

fun String?.getAvailableBalanceWithFormat(): SpannableString {
    return this?.trim()?.let { balance ->
        try {
            SpannableString(balance.toFormattedCurrency(showCurrency = false)).let { formattedBalance ->
                if (formattedBalance.isNotEmpty() && formattedBalance.contains(".")) {
                    formattedBalance.split(".").let { spllitedBalance ->
                        formattedBalance.setSpan(
                            RelativeSizeSpan(0.5f),
                            spllitedBalance[0].length,
                            spllitedBalance[0].length + spllitedBalance[1].length + 1,
                            0
                        )
                        return formattedBalance
                    }
                } else {
                    return SpannableString("")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return SpannableString("")
        }
    } ?: SpannableString("")
}
