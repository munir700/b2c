package co.yap.networking.transactions.requestdtos

data class TopUpTransactionRequest(
    val order: Order,
    val beneficiaryId: Int?,
    val securityCode: String?,
    val `3DSecureId`: String?
)