package co.yap.dashboard.transaction

import co.yap.app.YAPApplication
import co.yap.base.BaseTestCase
import co.yap.modules.dashboard.transaction.detail.TransactionDetailsViewModel
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.R
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionProductType
import co.yap.yapcore.helpers.extentions.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class TransactionDetailsViewModelTest : BaseTestCase() {
    lateinit var sut: TransactionDetailsViewModel

    data class TransactionTest(
        val transaction: Transaction,
        val detailExpectation: TransactionExpectation
    )

    data class TransactionExpectation(
        val title: String,
        val detailTransferType: String,
        val transferCategory: String,
        val location: String? = "",
        val currency: String,
        val amount: Double,
        val foreignAmount: Double,
        val spentAmount: Double
    )

    @BeforeEach
    override fun setUp() {
        super.setUp()
        sut = TransactionDetailsViewModel(YAPApplication())
    }

    @TestFactory
    fun test_add_receipt_section_should_show_or_not(): Collection<DynamicTest>? {
        val tests: MutableSet<DynamicTest> = LinkedHashSet()
        getTransactions().forEach {
            val expectedValue = when (it.transaction.productCode) {
                TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.ECOM.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.ATM_DEPOSIT.pCode -> true
                else -> false
            }
            tests.add(addReceiptNewTest(it.transaction, expectedValue))
        }
        return tests
    }

    @Test
    fun test_receipt_item_name() {
        val expectedValue = "Receipt 1"
        val actualValue = sut.receiptItemName(0)
        Assert.assertEquals(expectedValue, actualValue)
    }

    private fun addReceiptNewTest(
        transaction: Transaction,
        expectation: Boolean
    ): DynamicTest {
        val displayName: String = java.lang.String.format(
            Locale.getDefault(),
            "test_receipt_visibility_for_product_code_%s",
            transaction.productCode
        )
        return DynamicTest.dynamicTest(displayName) {
            Assert.assertEquals(expectation, sut.isShowReceiptSection(transaction))
        }
    }

    @TestFactory
    fun test_transaction(): Collection<DynamicTest>? {
        val tests: MutableSet<DynamicTest> = LinkedHashSet()
        getTransactions().forEach {
            tests.add(addNewTest(it.transaction, it.detailExpectation))
        }
        return tests
    }

    private fun addNewTest(
        transaction: Transaction,
        expectation: TransactionExpectation
    ): DynamicTest {
        val displayName: String = java.lang.String.format(
            Locale.getDefault(),
            "test_transaction_for_product_code_%s",
            transaction.productCode
        )
        return DynamicTest.dynamicTest(displayName) {
            Assert.assertEquals(expectation.detailTransferType, transaction.getStatusType())
            Assert.assertEquals(
                expectation.transferCategory,
                transaction.getTransferCategoryTitle()
            )
            Assert.assertEquals(
                expectedTransferCategoryIcon(transaction),
                transaction.getTransferCategoryIcon()
            )
            Assert.assertEquals(
                expectation.amount,
                transaction.getCalculatedTotalAmount(), 0.2
            )
            Assert.assertEquals(
                expectation.spentAmount,
                transaction.getSpentAmount(), 0.2
            )
            Assert.assertEquals(
                expectation.foreignAmount,
                sut.getForeignAmount(transaction), 0.2
            )
            Assert.assertEquals(
                expectation.location ?: "",
                transaction.getLocation()
            )
            Assert.assertEquals(
                getExpectedStatusIcon(transaction),
                transaction.getTransactionStatusIcon()
            )
        }
    }

    private fun expectedTransferCategoryIcon(transaction: Transaction?): Int {
        transaction?.let {
            if (transaction.getProductType() == TransactionProductType.IS_TRANSACTION_FEE) {
                return R.drawable.ic_expense
            }
            return (when (transaction.productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> R.drawable.ic_send_money
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> 0
                TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                    R.drawable.ic_send_money
                }
                TransactionProductCode.CARD_REORDER.pCode -> R.drawable.ic_expense
                TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.FUND_LOAD.pCode, TransactionProductCode.ATM_DEPOSIT.pCode -> {
                    R.drawable.ic_cash
                }
                TransactionProductCode.POS_PURCHASE.pCode -> if (transaction.merchantCategoryName.getMerchantCategoryIcon() == -1) R.drawable.ic_other_outgoing else transaction.merchantCategoryName.getMerchantCategoryIcon()

                else -> 0
            })
        } ?: return 0
    }


    private fun getExpectedStatusIcon(transaction: Transaction): Int {
        return when (transaction.productCode) {
            TransactionProductCode.ATM_WITHDRAWL.pCode -> {
                R.drawable.ic_identifier_atm_withdrawl
            }
            TransactionProductCode.ATM_DEPOSIT.pCode -> {
                R.drawable.ic_identifier_atm_deposite
            }

            else -> android.R.color.transparent
        }
    }

    private fun getTransactions(): List<TransactionTest> {
        val gson = GsonBuilder().create();
        val itemType = object : TypeToken<List<TransactionTest>>() {}.type

        return gson.fromJson<List<TransactionTest>>(readJsonFile(), itemType)
    }

    @Throws(IOException::class)
    private fun readJsonFile(): String? {
        val br =
            BufferedReader(InputStreamReader(FileInputStream("../yapcore/src/main/assets/jsons/transaction.json")))
        val sb = StringBuilder()
        var line: String? = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }
}