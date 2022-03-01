package com.yap.yappakistan

import androidx.lifecycle.MutableLiveData
import com.yap.core.utils.*
import com.yap.yappakistan.networking.apiclient.base.ApiResponse
import com.yap.yappakistan.networking.apiclient.base.CookiesManager
import com.yap.yappakistan.networking.microservices.authentication.AuthApi
import com.yap.yappakistan.networking.microservices.customers.CustomersApi
import com.yap.yappakistan.networking.microservices.customers.responsedtos.accountinfo.AccountInfo
import java.util.*

class SessionManager constructor(
    private val customersApi: CustomersApi,
    private val authApi: AuthApi,
    private val sharedPreferenceManager: SharedPreferenceManager
) {
    private var userAccounts: MutableLiveData<ArrayList<AccountInfo>> = MutableLiveData()
    val userAccount: MutableLiveData<AccountInfo> = MutableLiveData()
    var accountBalance: MutableLiveData<String>? = MutableLiveData()

    suspend fun getAccountInfo(error: (message: String?) -> Unit = {}) {
        when (val response = customersApi.getAccountInfo()) {
            is ApiResponse.Success -> {
                userAccounts.value = response.data.data as ArrayList<AccountInfo>
                userAccount.value =
                    userAccounts.value?.firstOrNull { account -> account.accountType == "B2C_ACCOUNT" }
                error.invoke(null)
            }
            is ApiResponse.Error -> {
                error.invoke(response.error.message)
                userAccounts.value = null
            }
        }
    }

    fun expireUserSession() {
        val isFirstTimeUser: Boolean =
            sharedPreferenceManager.getValueBoolien(
                KEY_IS_FIRST_TIME_USER,
                false
            )
        var userName: String? = ""
        var countryCode: String? = ""
        val isRemember = sharedPreferenceManager.getValueBoolien(KEY_IS_REMEMBER, false)
        if (isRemember) {
            userName = sharedPreferenceManager.getDecryptedUserName()
            countryCode = sharedPreferenceManager.getDecryptedUserDialCode().toString()
        }

        sharedPreferenceManager.clearSharedPreference()
        CookiesManager.jwtToken = null
        CookiesManager.isLoggedIn = false
        sharedPreferenceManager.save(
            KEY_APP_UUID,
            UUID.randomUUID().toString()
        )

        if (isRemember) {
            sharedPreferenceManager.saveUserNameWithEncryption(userName ?: "")
            sharedPreferenceManager.saveUserDialCodeWithEncryption(countryCode ?: "").toString()
        }

        sharedPreferenceManager.save(KEY_IS_REMEMBER, isRemember)
        sharedPreferenceManager.save(
            KEY_IS_FIRST_TIME_USER,
            isFirstTimeUser
        )

        sharedPreferenceManager.save(
            KEY_IS_USER_LOGGED_IN,
            false
        )
        sharedPreferenceManager.save(
            KEY_TOUCH_ID_ENABLED,
            false
        )

        userAccounts.value = null
        userAccount.value = null
    }

    suspend fun doLogOut(accountUUID: String, error: (message: String?) -> Unit = {}) {
        when (val response = authApi.logout(accountUUID)) {
            is ApiResponse.Success -> {
                expireUserSession()
                error.invoke(null)
            }
            is ApiResponse.Error -> {
                error.invoke(response.error.message)
            }
        }
    }
}