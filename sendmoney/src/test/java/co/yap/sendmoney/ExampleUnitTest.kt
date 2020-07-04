package co.yap.sendMoney

import co.yap.yapcore.helpers.extentions.roundVal
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4.1, 2 + 2)
    }

    @Test
    fun isValidRoundUp() {
        val value: Double = 101.385
        value.roundVal().toString()
        assertEquals("101.39", value.roundVal().toString())
    }
}
