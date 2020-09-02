package co.yap.sendmoney

import co.yap.yapcore.helpers.extentions.toFormattedCurrency2
import org.junit.Assert
import org.junit.Test

class FundTransferTest {

    @Test
    fun getConfigurableLength() {
        val aa = 4
        var empty = ""
        for (i in 1..aa) {
            empty = "0${empty}"
        }
        Assert.assertEquals(
            true,
            empty == "0000"
        )
    }
//
//    @Test
//    fun isAmountDecimalConfigured() {
//
//        val configuredDecimal = 2
//        val amount = "1000.1232"
//        Assert.assertEquals(
//            true,
//            amount.toFormattedCurrency2(configuredDecimal) == "1,000.1232"
//        )
//    }
//
//
//    @Test
//    fun isValidCPErrorMessage() {
//        val cp = SMCoolingPeriod()
//        cp.maxAllowedCoolingPeriodAmount = "10000.00"
//        cp.consumedAmount = 10000.00
//
//        Assert.assertEquals(
//            true,
//            cp.consumedAmount ?: 0.0 >= cp.maxAllowedCoolingPeriodAmount.parseToDouble()
//        )
//    }

//    @Test
//    fun isCoolingPeriodAmountConsumed() {
////        val cp = SMCoolingPeriod()
//        cp.maxAllowedCoolingPeriodAmount = "10000.00"
//        cp.consumedAmount = 0.00
//        val inputAmount = 10001.00
//        val remainingLimit = cp.maxAllowedCoolingPeriodAmount.parseToDouble()
//            .minus(cp.consumedAmount ?: 0.0)
//
//        Assert.assertEquals(
//            true,
//            inputAmount > remainingLimit
//        )
//    }
}