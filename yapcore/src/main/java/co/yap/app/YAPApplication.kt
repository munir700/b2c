package co.yap.app

import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.yapcore.config.BuildConfigManager
import dagger.android.support.DaggerApplication


abstract class YAPApplication : DaggerApplication() {
    companion object {
        var AUTO_RESTART_APP = true
        var configManager: BuildConfigManager? = null
        const val pageSize = 200
        var hasFilterStateChanged = false
        var homeTransactionsRequest: HomeTransactionsRequest = HomeTransactionsRequest(
            0,
            pageSize,
            null,
            null,
            null,
            null,
            totalAppliedFilter = 0,
            categories = arrayListOf(),
            statues = arrayListOf()
        )

        fun clearFilters() {
            homeTransactionsRequest = HomeTransactionsRequest(
                0, pageSize,
                null, null,
                null, null,
                0,
                categories = null,
                statues = null
            )
        }
    }
}