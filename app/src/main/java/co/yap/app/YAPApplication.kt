package co.yap.app

import android.app.Application
import co.yap.networking.RetroNetwork
import co.yap.yapcore.helpers.NetworkConnectionManager

class YAPApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetroNetwork.initWith(this, BuildConfig.BASE_URL)
        NetworkConnectionManager.init(this) // TODO: handle destroy of NetworkConnectionManager when app destroys
    }

}