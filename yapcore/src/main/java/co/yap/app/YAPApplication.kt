package co.yap.app

import android.app.Application
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.yapcore.helpers.AppInfo

open class YAPApplication(myAppInfo: AppInfo) : Application() {
    companion object {
        var AUTO_RESTART_APP = true
        var appInfo: AppInfo? = null
        const val pageSize = 10
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

    init {
        appInfo = myAppInfo
    }

}