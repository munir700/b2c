package co.yap.modules.dashboard.transaction.totalpurchases

import android.app.Application
import co.yap.modules.dashboard.home.adaptor.TransactionsListingAdapter
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.helpers.TransactionAdapterType

class TotalPurchasesViewModel(application: Application) :
    BaseViewModel<ITotalPurchases.State>(application), ITotalPurchases.ViewModel {
    override val adapter: TransactionsListingAdapter = TransactionsListingAdapter(
        arrayListOf(),
        TransactionAdapterType.TRANSACTION
    )

    val repository: TransactionsRepository = TransactionsRepository
    var list: MutableList<Transaction> = arrayListOf()
    override val state: ITotalPurchases.State = TotalPurchaseState()
    override fun onCreate() {
        adapter.setList(getTotalPurchase())
        super.onCreate()
    }

    private fun getTotalPurchase(): List<Transaction> {
        var transaction = Transaction()
        transaction.title = "Starbucks"

        list.add(transaction)
        list.add(transaction)
        list.add(transaction)
        list.add(transaction)
        list.add(transaction)
        list.add(transaction)
        return list
    }
}