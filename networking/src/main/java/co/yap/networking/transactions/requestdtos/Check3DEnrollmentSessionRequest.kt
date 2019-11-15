package co.yap.networking.transactions.requestdtos

import co.yap.networking.customers.models.Session

data class Check3DEnrollmentSessionRequest(val beneficiaryId:Int, val order: Order, val session: Session) {
}