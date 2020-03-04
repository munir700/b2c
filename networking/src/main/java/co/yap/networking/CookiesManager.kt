package co.yap.networking

import java.util.HashSet

 object CookiesManager {
    var jwtToken: String? = null
    var isLoggedIn: Boolean = false
    var cookieSet: HashSet<String> = HashSet()
}