package co.yap.sendMoney.addbeneficiary.models

import androidx.annotation.Keep

@Keep
enum class MoneyTransferType {
    BANK_TRANSFER, CASH_PICKUP, NONE
}