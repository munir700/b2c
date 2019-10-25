package co.yap.modules.dashboard.store.paging.transactions

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import co.yap.modules.dashboard.home.models.TransactionModel
import co.yap.networking.transactions.TransactionsRepository

class TransactionsDataSourceFactory(private val storeRepo: TransactionsRepository) :
    DataSource.Factory<Long, TransactionModel>() {

    val storeDataSourceLiveData = MutableLiveData<TransactionsDataSource>()

    override fun create(): DataSource<Long, TransactionModel> {
        val storeDataSource = TransactionsDataSource(storeRepo)
        storeDataSourceLiveData.postValue(storeDataSource)
        return storeDataSource
    }
}