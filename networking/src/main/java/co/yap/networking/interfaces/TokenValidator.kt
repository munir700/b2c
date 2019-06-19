package co.yap.networking.interfaces

internal interface TokenValidator {
    var isLoggedIn: Boolean
    var jwtToken: String?
    fun invalidate()
}