package co.yap.modules.dashboard.store.paging.transactions

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import co.yap.modules.dashboard.viewmodels.YapHomeViewModel
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData

class TransactionsDataSourceFactory(private val storeRepo: TransactionsRepository,    val yapHomeViewModel: YapHomeViewModel) :
    DataSource.Factory<Long, HomeTransactionListData>() {

    val storeDataSourceLiveData = MutableLiveData<TransactionsDataSource>()

    override fun create(): DataSource<Long, HomeTransactionListData> {
        val storeDataSource = TransactionsDataSource(storeRepo, yapHomeViewModel)
        storeDataSourceLiveData.postValue(storeDataSource)
        return storeDataSource
    }
}