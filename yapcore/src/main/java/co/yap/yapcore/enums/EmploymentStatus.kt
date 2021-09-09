package co.yap.yapcore.enums

enum class EmploymentStatus(val status: String) {
    Salaried("Employed"),
    Self_employed("Self-Employed"),
    A("Salaried & Self-Employed"),
    OTHER("Other"),
    NONE("None")
}