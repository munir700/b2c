package co.yap.networking.transactions

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.requestdtos.*
import co.yap.networking.transactions.responsedtos.*
import co.yap.networking.transactions.responsedtos.topuptransactionsession.Check3DEnrollmentSessionResponse
import co.yap.networking.transactions.responsedtos.topuptransactionsession.CreateTransactionSessionResponseDTO
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse

object TransactionsRepository : BaseRepository(), TransactionsApi {

    const val URL_ADD_FUNDS = "/transactions/api/top-up"
    const val URL_REMOVE_FUNDS = "/transactions/api/withdraw"
    const val URL_FUND_TRANSFER_LIMITS = "/transactions/api/product/{product-code}/limits"
    const val URL_FUND_TRANSFER_DENOMINATIONS =
        "/transactions/api/product/{product-code}/denominations"
    const val URL_GET_CARD_FEE = "/transactions/api/fees/spare-card/subscription/{card-type}"
    const val URL_GET_CARD_STATEMENTS = "/transactions/api/card-statements"
    const val URL_Y2Y_FUNDS_TRANSFER = "/transactions/api/y2y"
    const val URL_ADD_EDIT_NOTE = "/transactions/api/transaction-note"
    const val URL_SEARCH_FILTER_AMOUNT = "/transactions/api/transactions/search-filter/amount"
    const val URL_GET_TRANSACTION_DETAILS =
        "/transactions/api/transaction/transactionId/{transactionId}"
    const val URL_GET_ACCOUNT_TRANSACTIONS =
        "/transactions/api/account-transactions/{number}/{size}/"
    const val URL_GET_CARD_TRANSACTIONS =
        "/transactions/api/cards-transactions/{number}/{size}/"
    const val URL_GET_FEE = "/transactions/api/fee"
    const val URL_CREATE_TRANSACTION_SESSION =
        "/transactions/api/mastercard/create-checkout-session"
    const val URL_CHECK_3Ds_ENROLLMENT_SESSION = "/transactions/api/mastercard/check-3ds-enrollment"
    const val URL_TOP_UP_TRANSACTION = "/transactions/api/mastercard/order-id/{order-id}"
    const val URL_SECURE_ID_POOLING =
        "/transactions/api/mastercard/retrieve-acs-results/3DSecureId/{secureId}"
    const val URL_GET_ANALYTICS_BY_MERCHANT_NAME =
        "/transactions/api/transaction/card/analytics-merchant-name"
    const val URL_GET_ANALYTICS_BY_CATEGORY_NAME =
        "/transactions/api/transaction/card/analytics-merchant-category"
    const val URL_GET_TRANSACTION_FEE_WITH_PRODUCT_CODE =
        "/transactions/api/product-codes/{product-code}/fees"

    const val URL_GET_INTERNATIONAL_TRANSACTION_REASON_LIST =
        "/transactions/api/product-codes/{product-code}/purpose-reasons"

    const val URL_GET_INTERNATIONAL_RX_RATE_LIST =
        "transactions/api/product-codes/{product-code}/fxRate"
    const val URL_CASH_PAYOUT_TRANSFER =
        "/transactions/api/cashpayout"
    const val URL_DOMESTIC_TRANSFER =
        "/transactions/api/yap-to-rak"

    private val api: TransactionsRetroService =
        RetroNetwork.createService(TransactionsRetroService::class.java)

    override suspend fun addFunds(addFundsRequest: AddFundsRequest): RetroApiResponse<AddRemoveFundsResponse> =
        executeSafely(call = { api.addFunds(addFundsRequest) })

    override suspend fun removeFunds(removeFundsResponse: RemoveFundsRequest): RetroApiResponse<AddRemoveFundsResponse> =
        executeSafely(call = { api.removeFunds(removeFundsResponse) })

    override suspend fun getFundTransferLimits(productCode: String): RetroApiResponse<FundTransferLimitsResponse> =
        executeSafely(call = { api.getFundTransferLimits(productCode) })

    override suspend fun getFundTransferDenominations(productCode: String): RetroApiResponse<FundTransferDenominationsResponse> =
        executeSafely(call = { api.getFundTransferDenominations(productCode) })

    override suspend fun getCardFee(cardType: String): RetroApiResponse<CardFeeResponse> =
        executeSafely(call = { api.getCardFee(cardType) })

    override suspend fun getTransactionFeeWithProductCode(
        productCode: String?,
        mRemittanceFeeRequest: RemittanceFeeRequest
    ): RetroApiResponse<RemittanceFeeResponse> =
        executeSafely(call = {
            api.getTransactionFeeWithProductCode(
                productCode,
                mRemittanceFeeRequest
            )
        })

    override suspend fun getTransactionInternationalReasonList(productCode: String): RetroApiResponse<InternationalFundsTransferReasonList> =
        executeSafely(call = { api.getInternationalTransactionReasonList(productCode) })

    override suspend fun getTransactionInternationalRXList(
        productCode: String,
        mRxListRequest: RxListRequest
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.getInternationalRXRateList(productCode, mRxListRequest) })

    override suspend fun getCardStatements(cardSerialNumber: String): RetroApiResponse<CardStatementsResponse> =
        executeSafely(call = { api.getCardStatements(cardSerialNumber) })

    override suspend fun y2yFundsTransferRequest(y2YFundsTransferRequest: Y2YFundsTransferRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.y2yFundsTransferRequest(y2YFundsTransferRequest) })

    override suspend fun addEditNote(addEditNoteRequest: AddEditNoteRequest): RetroApiResponse<AddEditNoteResponse> =
        executeSafely(call = { api.addEditNote(addEditNoteRequest) })

    override suspend fun getAccountTransactions(homeTransactionsRequest: HomeTransactionsRequest): RetroApiResponse<HomeTransactionsResponse> =
        executeSafely(call = {
            api.getAccountTransactions(
                homeTransactionsRequest.number,
                homeTransactionsRequest.size,
                homeTransactionsRequest.amountStartRange,
                homeTransactionsRequest.amountEndRange,
                homeTransactionsRequest.txnType,
                homeTransactionsRequest.title
            )
        })

    override suspend fun getSearchFilterAmount(): RetroApiResponse<SearchFilterAmountResponse> =
        executeSafely(call = { api.getSearchFilterAmount() })

    override suspend fun getTransactionDetails(transactionId: String): RetroApiResponse<TransactionDetailsResponse> =
        executeSafely(call = { api.getTransactionDetails(transactionId) })

    override suspend fun getCardTransactions(cardTransactionRequest: CardTransactionRequest): RetroApiResponse<HomeTransactionsResponse> =
        executeSafely(call = {
            api.getCardTransactions(
                cardTransactionRequest.number,
                cardTransactionRequest.size,
                cardTransactionRequest.serialNumber
            )
        })


    override suspend fun getTransactionFee(productCode: String): RetroApiResponse<TransactionFeeResponseDTO> =
        executeSafely(call = { api.getTransactionFee(productCode) })

    override suspend fun createTransactionSession(createSessionRequest: CreateSessionRequest): RetroApiResponse<CreateTransactionSessionResponseDTO> =
        executeSafely(call = { api.createTransactionSession(createSessionRequest) })

    override suspend fun check3DEnrollmentSession(check3DEnrollmentSessionRequest: Check3DEnrollmentSessionRequest): RetroApiResponse<Check3DEnrollmentSessionResponse> =
        executeSafely(call = { api.check3DEnrollmentSession(check3DEnrollmentSessionRequest) })

    override suspend fun secureIdPooling(
        secureId: String
    ): RetroApiResponse<StringDataResponseDTO> =
        executeSafely(call = { api.secureIdPooling(secureId) })

    override suspend fun cardTopUpTransactionRequest(
        orderId: String,
        topUpTransactionRequest: TopUpTransactionRequest
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.cardTopUpTransactionRequest(orderId, topUpTransactionRequest) })

    override suspend fun getAnalyticsByMerchantName(
        cardSerialNo: String?,
        date: String?
    ): RetroApiResponse<AnalyticsResponseDTO> =
        executeSafely(call = { api.getAnalyticsByMerchantName(cardSerialNo, date) })

    override suspend fun getAnalyticsByCategoryName(
        cardSerialNo: String?,
        date: String?
    ): RetroApiResponse<AnalyticsResponseDTO> =
        executeSafely(call = { api.getAnalyticsByCategoryName(cardSerialNo, date) })

    override suspend fun cashPayoutTransferRequest(cashPayoutRequestDTO: CashPayoutRequestDTO): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.cashPayoutTransferRequest(cashPayoutRequestDTO) })

    override suspend fun domesticTransferRequest(domesticTransactionRequestDTO: DomesticTransactionRequestDTO): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.domesticTransferRequest(domesticTransactionRequestDTO) })

}
