package co.yap.networking.transactions

import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.requestdtos.*
import co.yap.networking.transactions.responsedtos.*
import co.yap.networking.transactions.responsedtos.topuptransactionsession.Check3DEnrollmentSessionResponse
import co.yap.networking.transactions.responsedtos.topuptransactionsession.CreateTransactionSessionResponseDTO
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse

interface TransactionsApi {
    suspend fun addFunds(addFundsRequest: AddFundsRequest): RetroApiResponse<AddRemoveFundsResponse>
    suspend fun removeFunds(removeFundsResponse: RemoveFundsRequest): RetroApiResponse<AddRemoveFundsResponse>
    suspend fun getFundTransferLimits(productCode: String?): RetroApiResponse<FundTransferLimitsResponse>
    suspend fun getFundTransferDenominations(productCode: String): RetroApiResponse<FundTransferDenominationsResponse>
    suspend fun getCardFee(cardType: String): RetroApiResponse<CardFeeResponse>
    suspend fun getTransactionFeeWithProductCode(
        productCode: String?,
        mRemittanceFeeRequest: RemittanceFeeRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun getTransactionInternationalReasonList(productCode: String?): RetroApiResponse<InternationalFundsTransferReasonList>
    suspend fun getCardStatements(cardSerialNumber: String?): RetroApiResponse<CardStatementsResponse>
    suspend fun getTransactionInternationalRXList(
        RxNumber: String?,
        mRxListRequest: RxListRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun y2yFundsTransferRequest(y2YFundsTransferRequest: Y2YFundsTransferRequest): RetroApiResponse<ApiResponse>
    suspend fun addEditNote(addEditNoteRequest: AddEditNoteRequest): RetroApiResponse<AddEditNoteResponse>
    suspend fun getSearchFilterAmount(): RetroApiResponse<SearchFilterAmountResponse>
    suspend fun getTransactionDetails(transactionId: String?): RetroApiResponse<TransactionDetailsResponse>
    suspend fun getAccountTransactions(homeTransactionsRequest: HomeTransactionsRequest): RetroApiResponse<HomeTransactionsResponse>
    suspend fun getCardTransactions(cardTransactionRequest: CardTransactionRequest): RetroApiResponse<HomeTransactionsResponse>
    suspend fun getTransactionFee(productCode: String): RetroApiResponse<TransactionFeeResponseDTO>
    suspend fun createTransactionSession(createSessionRequest: CreateSessionRequest): RetroApiResponse<CreateTransactionSessionResponseDTO>
    suspend fun check3DEnrollmentSession(check3DEnrollmentSessionRequest: Check3DEnrollmentSessionRequest): RetroApiResponse<Check3DEnrollmentSessionResponse>
    suspend fun secureIdPooling(secureId: String = ""): RetroApiResponse<StringDataResponseDTO>
    suspend fun cardTopUpTransactionRequest(
        orderId: String,
        topUpTransactionRequest: TopUpTransactionRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun getAnalyticsByMerchantName(
        cardSerialNo: String? = "",
        date: String? = ""
    ): RetroApiResponse<AnalyticsResponseDTO>

    suspend fun getAnalyticsByCategoryName(
        cardSerialNo: String? = "",
        date: String? = ""
    ): RetroApiResponse<AnalyticsResponseDTO>

    suspend fun cashPayoutTransferRequest(cashPayoutRequestDTO: CashPayoutRequestDTO): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun domesticTransferRequest(domesticTransactionRequestDTO: DomesticTransactionRequestDTO): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun uaeftsTransferRequest(uaeftsTransactionRequestDTO: UAEFTSTransactionRequestDTO): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun rmtTransferRequest(rmtTransactionRequestDTO: RMTTransactionRequestDTO): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun swiftTransferRequest(swiftTransactionRequestDTO: SwiftTransactionRequestDTO): RetroApiResponse<SendMoneyTransactionResponseDTO>
    suspend fun getHousholdFeePackage(packageType:String):RetroApiResponse<CardFeeResponse>

}