package co.yap.yapcore.leanplum

data class UserAttributes(
    val accountType: String = "accountType",
    val email: String = "email",
    val nationality: String = "nationality",
    val firstName: String = "firstName",
    val lastName: String = "lastName",
    val documentsVerified: String = "documentsVerified",
    val mainUser: String = "mainUser",
    val householdUser: String = "householdUser",
    val youngUser: String = "youngUser",
    val b2bUser: String = "b2bUser"
)