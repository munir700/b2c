package co.yap.yapcore.helpers

import android.content.Context
import android.content.SharedPreferences
import co.yap.app.login.EncryptionUtils

class SharedPreferenceManager(val context: Context) {

    private val PREFS_NAME = "YAPPref"
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val KEY_APP_UUID = "KEY_APP_UUID"
        const val KEY_PASSCODE: String = "PASSCODE"
        const val KEY_USERNAME: String = "USEERNAME"
        const val KEY_TOUCH_ID_ENABLED: String = "TOUCH_ID_ENABLED"
        const val KEY_IS_USER_LOGGED_IN: String = "KEY_IS_USER_LOGGED_IN"
        const val KEY_IS_FIRST_TIME_USER: String = "KEY_IS_FIRST_TIME_USER"
        const val KEY_IS_FINGERPRINT_PERMISSION_SHOWN: String =
            "KEY_IS_FINGERPRINT_PERMISSION_SHOWN"
        const val KEY_AVAILABLE_BALANCE: String = "AVAILABLE_BALANCE"
    }

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor!!.apply()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status!!)
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

    fun saveUserName(text: String) {
//
//        sharedPreferenceManager.save(
//            KEY_USERNAME, EncryptionUtils.encrypt(context, text)!!
//        )
//        sharedPreferenceManager.save(
//            SharedPreferenceManager.KEY_PASSCODE,
//            EncryptionUtils.encrypt(context, SharedPreferenceManager.KEY_PASSCODE)!!
//        )

        if (!isNumeric(text)) {
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(KEY_USERNAME, EncryptionUtils.encrypt(context, text)!!)
            EncryptionUtils.encrypt(context, SharedPreferenceManager.KEY_PASSCODE)!!

            editor!!.apply()
        }
    }

    private fun isNumeric(str: String): Boolean {
        return str.matches("-?\\d+(\\.\\d+)?".toRegex())  //match a number with optional '-' and decimal.
    }

}
