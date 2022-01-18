package co.yap.networking.customers.responsedtos

enum class AmendmentStatus {
    SUBMIT_TO_ADMIN, SUBMIT_TO_CUSTOMER, SUBMIT_TO_BANK, RECEIVED_FROM_CUSTOMER, TAX_INFO_COLLECTED, EMP_INFO_COMPLETED, BIRTH_INFO_COLLECTED
}

enum class AmendmentSection(val value: String?) {
    EID_INFO("eidInfo"),
    BIRTH_INFO("birthInfo"),
    TAX_INFO("taxInfo"),
    EMPLOYMENT_INFO("empInfo"),
    PASSPORT_INFO("passportInfo")
}