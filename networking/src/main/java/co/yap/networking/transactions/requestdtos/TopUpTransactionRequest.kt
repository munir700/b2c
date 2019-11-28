package co.yap.networking.transactions.requestdtos

data class TopUpTransactionRequest(
    val `3DSecureId`: String?,
    val beneficiaryId: Int?,
    val order: Order,
    val securityCode: String?
)