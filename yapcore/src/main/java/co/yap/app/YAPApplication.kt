package co.yap.app

import android.app.Application
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest

open class YAPApplication : Application() {
    companion object {
        var AUTO_RESTART_APP = true
        var homeTransactionsRequest: HomeTransactionsRequest = HomeTransactionsRequest(
            1,
            200,
            0.00,
            20000.00,
            true,
            debitSearch = true,
            totalAppliedFilter = 0,
            yapYoungTransfer = true
        )
    }
}