package co.yap.yapcore.helpers

import android.content.Context
import co.yap.yapcore.constants.Constants

/**
 * Utility class for SharedPreferences related tasks
 */

object PreferenceUtils {

    /**
     * This method returns device id from shared preferences
     * @param context : Receives context object
     */
    fun getDeviceId(context: Context): String? {
        return SharedPreferenceManager.getInstance(context).getValueString(Constants.KEY_APP_UUID)
    }

}