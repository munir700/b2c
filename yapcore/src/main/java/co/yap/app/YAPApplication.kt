package co.yap.app

import android.app.Application
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest

open class YAPApplication : Application() {
    companion object {
        var AUTO_RESTART_APP = true
        const val pageSize = 100
        var homeTransactionsRequest: HomeTransactionsRequest = HomeTransactionsRequest(
            1,
            pageSize,
            null,
            null,
            null,
            null,
            totalAppliedFilter = 0,
            yapYoungTransfer = null
        )
    }
}