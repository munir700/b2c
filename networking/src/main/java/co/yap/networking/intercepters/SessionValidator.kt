package co.yap.networking.intercepters

import co.yap.networking.CookiesManager
import co.yap.networking.interfaces.TokenValidator
import okhttp3.Interceptor
import okhttp3.Response

internal abstract class SessionValidator : TokenValidator, Interceptor {
    override var isLoggedIn: Boolean
        get() = CookiesManager.isLoggedIn
        set(value) {
            CookiesManager.isLoggedIn = value
        }

    override var jwtToken: String?
        get() = CookiesManager.jwtToken
        set(value) {
            CookiesManager.jwtToken = value
        }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        // Check if user is logged in server revoked the access token.
        if (isLoggedIn && response.code() == 401) {
            // need to refresh the token since previous token was invalid
            // TODO: Implement token refresh logic here. For now let's invalidate
            invalidate()
        }

        return response
    }
}