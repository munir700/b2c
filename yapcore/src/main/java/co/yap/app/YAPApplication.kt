package co.yap.app

import android.app.Application
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest

open class YAPApplication(selectedflavour: String) : Application() {
    companion object {
        var AUTO_RESTART_APP = true
        var flavour = ""
        const val pageSize = 100
        var hasFilterStateChanged = false
        var isAllChecked = false
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
        flavour = selectedflavour
    }

}