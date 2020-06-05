package co.yap.app

import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.yapcore.helpers.AppInfo
import dagger.android.support.DaggerApplication

abstract class YAPApplication(myAppInfo: AppInfo) : DaggerApplication() {
    companion object {
        var AUTO_RESTART_APP = true
        var appInfo: AppInfo? = null
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

    init {
        appInfo = myAppInfo
    }

}