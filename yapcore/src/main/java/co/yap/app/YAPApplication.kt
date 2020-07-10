package co.yap.app

import android.app.Application
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.yapcore.config.BuildConfigManager

open class YAPApplication : Application() {
    companion object {
        var AUTO_RESTART_APP = true
        lateinit var configManager: BuildConfigManager
        const val pageSize = 200
        var hasFilterStateChanged = false
        var homeTransactionsRequest: HomeTransactionsRequest = HomeTransactionsRequest(
            0,
            pageSize,
            null,
            null,
            null,
            null,
            totalAppliedFilter = 0
        )


        fun clearFilters() {
            homeTransactionsRequest = HomeTransactionsRequest(
                0, pageSize,
                null, null,
                null, null,
                0
            )
        }
    }
}