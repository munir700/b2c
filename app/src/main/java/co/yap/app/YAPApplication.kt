package co.yap.app

import android.app.Application
import co.yap.app.BuildConfig
import co.yap.networking.RetroNetwork

class YAPApplication : Application() {

//    init {
////        NetworkConnectionManager.init(this) // TODO: handle destroy of NetworkConnectionManager when app destroys
//        RetroNetwork.initWith(this, BuildConfig.BASE_URL)
//    }

    override fun onCreate() {
        super.onCreate()
        RetroNetwork.initWith(this, BuildConfig.BASE_URL)
    }

}