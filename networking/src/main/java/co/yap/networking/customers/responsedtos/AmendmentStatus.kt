package co.yap.networking.customers.responsedtos

enum class AmendmentStatus {
    SUBMIT_TO_ADMIN, SUBMIT_TO_CUSTOMER, SUBMIT_TO_BANK, RECEIVED_FROM_CUSTOMER
}

enum class AmendmentSection(val value: String?) {
    EID_INFO("eidInfo"),
    BIRTH_INFO("birthInfo"),
    TAX_INFO("taxInfo"),
    EMPLOYMENT_INFO("empInfo")
}