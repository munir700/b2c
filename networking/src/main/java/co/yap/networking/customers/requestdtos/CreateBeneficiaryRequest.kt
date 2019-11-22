package co.yap.networking.customers.requestdtos

import co.yap.networking.customers.models.Session

data class CreateBeneficiaryRequest(val alias: String, val color: String, val session: Session)