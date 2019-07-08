package co.yap.app

import android.app.Application
import android.content.Context
import co.yap.networking.RetroNetwork
import co.yap.yapcore.helpers.NetworkConnectionManager
import co.yap.yapcore.helpers.SharedPreferenceManager
import java.util.*

class YAPApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetroNetwork.initWith(this, BuildConfig.BASE_URL)
        NetworkConnectionManager.init(this) // TODO: handle destroy of NetworkConnectionManager when app destroys
        setAppUniqueId(this)
    }


    fun setAppUniqueId(context: Context) {
        var uuid: String?
        val sharedPrefs = SharedPreferenceManager(context)
        uuid = sharedPrefs.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        if (uuid == null) {
            uuid = UUID.randomUUID().toString()
            sharedPrefs.save(SharedPreferenceManager.KEY_APP_UUID, uuid)
        }
    }

}