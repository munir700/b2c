package co.yap.modules.dashboard.store.paging.transactions

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import co.yap.modules.dashboard.home.viewmodels.YapHomeViewModel
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData

class TransactionsDataSourceFactory(private val storeRepo: TransactionsRepository,    val yapHomeViewModel: YapHomeViewModel) :
    DataSource.Factory<Long, HomeTransactionListData>() {

    val transactionDataSourceLiveData = MutableLiveData<TransactionsDataSource>()

    override fun create(): DataSource<Long, HomeTransactionListData> {
        val transactionsDataSource = TransactionsDataSource(storeRepo, yapHomeViewModel)
        transactionDataSourceLiveData.postValue(transactionsDataSource)
        return transactionsDataSource
    }
}