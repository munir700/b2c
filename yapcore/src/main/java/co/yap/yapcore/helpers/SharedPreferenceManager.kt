package co.yap.yapcore.helpers

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(val context: Context) {

    private val PREFS_NAME = "YAPPref"


    companion object {
        const val KEY_APP_UUID = "KEY_APP_UUID"
        const val KEY_PASSCODE: String = "PASSCODE"
        const val KEY_USERNAME: String = "USEERNAME"
        const val KEY_TOUCH_ID_ENABLED: String = "TOUCH_ID_ENABLED"
        const val KEY_IS_USER_LOGGED_IN: String = "KEY_IS_USER_LOGGED_IN"
        const val KEY_IS_FINGERPRINT_PERMISSION_SHOWN: String = "KEY_IS_FINGERPRINT_PERMISSION_SHOWN"
    }



    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor!!.commit()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.commit()
    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status!!)
        editor.commit()
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
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }
}
