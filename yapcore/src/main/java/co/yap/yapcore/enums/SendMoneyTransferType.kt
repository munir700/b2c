package co.yap.yapcore.enums

enum class SendMoneyTransferType(val type : String) {
    LOCAL("Local Bank"),
    INTERNATIONAL ("International Transfer"),
    HOME("Home Country")
}