package co.yap.yapcore.enums

sealed class SystemConfigurations(val key: String) {
    object ENABLE_RMT_SWIFT_FEATURE : SystemConfigurations("ENABLE_RMT_SWIFT_FEATURE")
    object ENABLE_ENABLE_FSS_TIME_OUT : SystemConfigurations("ENABLE_ENABLE_FSS_TIME_OUT")
    object MINIMUM_SALARY : SystemConfigurations("MINIMUM_SALARY")
    object DEFAULT_SALARY : SystemConfigurations("DEFAULT_SALARY")
    object MINIMUM_CASH_DEPOSIT : SystemConfigurations("MINIMUM_CASH_DEPOSIT")
    object DEFAULT_CASH_DEPOSIT : SystemConfigurations("DEFAULT_CASH_DEPOSIT")
    object ENABLE_PASSPORT_AMENDMENT : SystemConfigurations("ENABLE_PASSPORT_AMENDMENT")
    object EID_EXPIRE_LIMIT_DAYS : SystemConfigurations("EID_EXPIRE_LIMIT_DAYS")
}