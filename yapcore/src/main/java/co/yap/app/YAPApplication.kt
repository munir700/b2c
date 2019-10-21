package co.yap.app

import android.app.Application

open class YAPApplication : Application() {
    companion object {
        var AUTO_RESTART_APP = true
    }
}