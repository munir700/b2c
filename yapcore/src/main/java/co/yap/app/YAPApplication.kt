package co.yap.app

import android.app.Application
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest

open class YAPApplication : Application() {
    companion object {
        var AUTO_RESTART_APP = true
       var homeTransactionsRequest: HomeTransactionsRequest?=null
    }
}