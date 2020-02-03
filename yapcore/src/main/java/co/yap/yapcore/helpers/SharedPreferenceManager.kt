package co.yap.yapcore.helpers

import android.content.Context
import android.content.SharedPreferences
import co.yap.yapcore.helpers.encryption.EncryptionUtils

class SharedPreferenceManager(val context: Context) {

    private val PREFS_NAME = "YAPPref"
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val KEY_APP_UUID = "KEY_APP_UUID"
        private const val KEY_PASSCODE: String = "PASSCODE"
        private const val KEY_USERNAME: String = "USEERNAME"
        const val KEY_TOUCH_ID_ENABLED: String = "TOUCH_ID_ENABLED"
        const val KEY_IS_USER_LOGGED_IN: String = "KEY_IS_USER_LOGGED_IN"
        const val KEY_IS_FIRST_TIME_USER: String = "KEY_IS_FIRST_TIME_USER"
        const val KEY_IS_FINGERPRINT_PERMISSION_SHOWN: String =
            "KEY_IS_FINGERPRINT_PERMISSION_SHOWN"
        const val KEY_AVAILABLE_BALANCE: String = "AVAILABLE_BALANCE"
        const val KEY_THEME = "KEY_THEME"

    }

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status)
        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

    fun saveUserNameWithEncryption(text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        EncryptionUtils.encrypt(context, text)?.let {
            editor.putString(KEY_USERNAME, it)
            editor.apply()
        }
    }

    private fun isNumeric(str: String): Boolean {
        return str.matches("-?\\d+(\\.\\d+)?".toRegex())  //match a number with optional '-' and decimal.
    }

    fun getDecryptedUserName(): String? {
        SharedPreferenceManager(context).getValueString(KEY_USERNAME)?.let {
            return EncryptionUtils.decrypt(
                context,
                it
            )?.let { user_name ->
                return user_name
            }
                ?: return null
        } ?: return null
    }

    fun savePassCodeWithEncryption(text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        EncryptionUtils.encrypt(context, text)?.let {
            editor.putString(KEY_PASSCODE, it)
            editor.apply()
        }
    }

    fun getDecryptedPassCode(): String? {
        SharedPreferenceManager(context).getValueString(KEY_PASSCODE)?.let {
            return EncryptionUtils.decrypt(context, it)?.let { passcode ->
                return passcode
            }
                ?: return null
        } ?: return null


    }

    fun getThemeValue(): String? {
        return sharedPref.getString(KEY_THEME, "")
    }

    fun setThemeValue(themeValue: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_THEME, themeValue)
        editor.apply()
    }
}
