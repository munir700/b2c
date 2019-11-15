package co.yap.networking.transactions.responsedtos.topuptransactionsession

import co.yap.networking.customers.models.Session

data class CreateSessionResponseObject(val order: OrderResponseDTO, val session:Session) {
}