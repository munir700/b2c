package co.yap.sendmoney

import co.yap.networking.customers.responsedtos.sendmoney.SMCoolingPeriod
import co.yap.yapcore.helpers.extentions.parseToDouble
import org.junit.Assert
import org.junit.Test

class FundTransferTest {


    @Test
    fun isValidCPErrorMessage() {
        val cp = SMCoolingPeriod()
        cp.maxAllowedCoolingPeriodAmount = "10000.00"
        cp.consumedAmount = 10000.00

        Assert.assertEquals(
            true,
            cp.consumedAmount ?: 0.0 >= cp.maxAllowedCoolingPeriodAmount.parseToDouble()
        )
    }
}