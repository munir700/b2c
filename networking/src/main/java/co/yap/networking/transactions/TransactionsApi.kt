package co.yap.networking.transactions

import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.networking.transactions.requestdtos.*
import co.yap.networking.transactions.responsedtos.*
import co.yap.networking.transactions.responsedtos.categorybar.CategoryBarResponse
import co.yap.networking.transactions.responsedtos.billpayment.BillAccountHistoryResponse
import co.yap.networking.transactions.responsedtos.billpayment.BillLineChartHistory
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsDetailsDTO
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsResponseDTO
import co.yap.networking.transactions.responsedtos.payallbills.PayAllBillsResponse
import co.yap.networking.transactions.responsedtos.purposepayment.PaymentPurposeResponseDTO
import co.yap.networking.transactions.responsedtos.topuptransactionsession.Check3DEnrollmentSessionResponse
import co.yap.networking.transactions.responsedtos.topuptransactionsession.CreateTransactionSessionResponseDTO
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.networking.transactions.responsedtos.transaction.TransactionCategoryResponse
import co.yap.networking.transactions.responsedtos.transaction.TransactionDataResponseForLeanplum
import co.yap.networking.transactions.responsedtos.transactionreciept.TransactionReceiptResponse
import okhttp3.MultipartBody
import java.util.*

interface TransactionsApi {
    suspend fun addFunds(addFundsRequest: AddFundsRequest): RetroApiResponse<AddRemoveFundsResponse>
    suspend fun removeFunds(removeFundsResponse: RemoveFundsRequest): RetroApiResponse<AddRemoveFundsResponse>
    suspend fun getFundTransferLimits(productCode: String?,accountUuuid : String?): RetroApiResponse<FundTransferLimitsResponse>
    suspend fun getFundTransferDenominations(productCode: String): RetroApiResponse<FundTransferDenominationsResponse>
    suspend fun getCardFee(cardType: String): RetroApiResponse<RemittanceFeeResponse>
    suspend fun getDebitCardFee(): RetroApiResponse<RemittanceFeeResponse>
    suspend fun getTransactionFeeWithProductCode(
        productCode: String?,
        mRemittanceFeeRequest: RemittanceFeeRequest?
    ): RetroApiResponse<ApiResponse>

    suspend fun getTransactionInternationalReasonList(productCode: String?): RetroApiResponse<InternationalFundsTransferReasonList>
    suspend fun getCardStatements(cardSerialNumber: String?): RetroApiResponse<CardStatementsResponse>
    suspend fun getAccountStatements(): RetroApiResponse<CardStatementsResponse>
    suspend fun getTransactionInternationalRXList(
        productCode: String?,
        mRxListRequest: RxListRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun y2yFundsTransferRequest(y2YFundsTransferRequest: Y2YFundsTransferRequest): RetroApiResponse<ApiResponse>
    suspend fun addEditNote(addEditNoteRequest: AddEditNoteRequest): RetroApiResponse<AddEditNoteResponse>
    suspend fun getSearchFilterAmount(): RetroApiResponse<SearchFilterAmountResponse>
    suspend fun getTransactionDetails(transactionId: String?): RetroApiResponse<TransactionDetailsResponse>
    suspend fun getAccountTransactions(homeTransactionsRequest: HomeTransactionsRequest?): RetroApiResponse<HomeTransactionsResponse>
    suspend fun searchTransactions(homeTransactionsRequest: HomeTransactionsRequest?): RetroApiResponse<HomeTransactionsResponse>
    suspend fun getCardTransactions(cardTransactionRequest: CardTransactionRequest): RetroApiResponse<HomeTransactionsResponse>
    suspend fun getTransactionFee(productCode: String): RetroApiResponse<TransactionFeeResponseDTO>
    suspend fun createTransactionSession(createSessionRequest: CreateSessionRequest): RetroApiResponse<CreateTransactionSessionResponseDTO>
    suspend fun check3DEnrollmentSession(check3DEnrollmentSessionRequest: Check3DEnrollmentSessionRequest): RetroApiResponse<Check3DEnrollmentSessionResponse>
    suspend fun secureIdPooling(secureId: String? = ""): RetroApiResponse<StringDataResponseDTO>
    suspend fun cardTopUpTransactionRequest(
        orderId: String,
        topUpTransactionRequest: TopUpTransactionRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun getAnalyticsByMerchantName(
        date: String? = ""
    ): RetroApiResponse<AnalyticsResponseDTO>

    suspend fun getAnalyticsByCategoryName(
        date: String? = ""
    ): RetroApiResponse<AnalyticsResponseDTO>

    suspend fun cashPayoutTransferRequest(sendMoneyTransferRequest: SendMoneyTransferRequest): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun domesticTransferRequest(sendMoneyTransferRequest: SendMoneyTransferRequest): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun uaeftsTransferRequest(sendMoneyTransferRequest: SendMoneyTransferRequest): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun rmtTransferRequest(sendMoneyTransferRequest: SendMoneyTransferRequest): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun swiftTransferRequest(sendMoneyTransferRequest: SendMoneyTransferRequest): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun getTransactionThresholds(): RetroApiResponse<TransactionThresholdResponseDTO>
    suspend fun getCutOffTimeConfiguration(
        productCode: String?,
        currency: String?,
        amount: String?,
        isCbwsi: Boolean? = null
    ): RetroApiResponse<CutOffTime>

    suspend fun getAchievements(): RetroApiResponse<ApiResponse>
    suspend fun getPurposeOfPayment(productCode: String): RetroApiResponse<PaymentPurposeResponseDTO>
    suspend fun checkCoolingPeriodRequest(
        beneficiaryId: String?,
        beneficiaryCreationDate: String?,
        beneficiaryName: String?,
        amount: String?
    ): RetroApiResponse<ApiResponse>

    suspend fun getTransactionsOfMerchant(
        merchantType: String,
        cardSerialNo: String?,
        date: String?, merchantName: ArrayList<Any>?

    ): RetroApiResponse<AnalyticsDetailResponseDTO>

    suspend fun getAllTransactionReceipts(transactionId: String): RetroApiResponse<TransactionReceiptResponse>

    suspend fun addTransactionReceipt(
        transactionId: String,
        transactionReceipt: MultipartBody.Part
    ): RetroApiResponse<ApiResponse>

    suspend fun updateTransactionReceipt(transactionId: String): RetroApiResponse<ApiResponse>
    suspend fun deleteTransactionReceipt(
        transactionId: String,
        receipt: String
    ): RetroApiResponse<ApiResponse>

    suspend fun getTransDetailForLeanplum(): RetroApiResponse<TransactionDataResponseForLeanplum>

    suspend fun getTotalPurchases(
        totalPurchaseRequest: TotalPurchaseRequest
    ): RetroApiResponse<TotalPurchasesResponse>

    suspend fun getAllTransactionCategories(): RetroApiResponse<TransactionCategoryResponse>
    suspend fun updateTransactionCategory(
        categoryId: String,
        transactionId: String
    ): RetroApiResponse<ApiResponse>

    suspend fun requestSendEmail(
        sendEmailRequestModel: SendEmailRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun getTotalPurchasesList(totalPurchaseRequest: TotalPurchaseRequest): RetroApiResponse<TotalPurchasesTransactionResponse>

    suspend fun requestCategoryBarData(): RetroApiResponse<CategoryBarResponse>

    //Pay bill from bill payment
    suspend fun payBill(payBillRequest: PayBillRequest): RetroApiResponse<ApiResponse>
    suspend fun fetchCustomerBillHistory(customerBillUuid: String): RetroApiResponse<BillAccountHistoryResponse>
    suspend fun getBPAnalytics(date: String?): RetroApiResponse<BPAnalyticsResponseDTO>
    suspend fun getBPLineChartHistory(customerBillUuid: String): RetroApiResponse<BaseListResponse<BillLineChartHistory>>
    suspend fun getBPCategoryHistory(
        month: String?,
        categoryId: String?
    ): RetroApiResponse<BPAnalyticsDetailsDTO>

    suspend fun payAllBills(payAllBillsRequest: ArrayList<PayAllRequest>): RetroApiResponse<PayAllBillsResponse>

    //    House Hold API calls fees/subscriptions
    suspend fun getPrepaidUserSubscriptionsPlans(
        productPlan: String,
        feeFrequency: String
    ): RetroApiResponse<RemittanceFeeResponse>

    //    House Hold Pay Salary Now
    suspend fun paySalaryNow(request: PaySalaryNowRequest): RetroApiResponse<ApiResponse>
    suspend fun getFailedTransactions(): RetroApiResponse<BaseListResponse<HomeNotification>>
    suspend fun getHouseHoldAccountStatements(householdAccountUUID: String?): RetroApiResponse<CardStatementsResponse>
    suspend fun getHouseHoldAccountTransactions(homeTransactionsRequest: HomeTransactionsRequest?): RetroApiResponse<HomeTransactionsResponse>
}
