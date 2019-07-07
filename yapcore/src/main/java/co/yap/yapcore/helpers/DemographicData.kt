package co.yap.yapcore.helpers

import android.content.Context
import android.os.Build

import java.util.UUID

class DemographicData {

    companion object {

        private val PREF_DEVICE_ID = "PREF_DEVICE_ID"
        private val ACTION = "LOGIN"
        private val OS_VERSION = Build.VERSION.RELEASE
        private var DEVICE_ID: String? = ""
        private val DEVICE_NAME = Build.BRAND
        private val DEVICE_MODEL = Build.MODEL
        private val OS_TYPE = "Android"
    }



    @Synchronized
    fun getDeviceId(context: Context): String {
        if (DEVICE_ID == "") {
            val sharedPrefs = SharedPreferenceManager(context)
            DEVICE_ID = sharedPrefs.getValueString(PREF_DEVICE_ID)
            if (DEVICE_ID == null) {
                DEVICE_ID = UUID.randomUUID().toString()
                sharedPrefs.save(PREF_DEVICE_ID, DEVICE_ID!!)
            }
        }
        return DEVICE_ID.toString()
    }

}
