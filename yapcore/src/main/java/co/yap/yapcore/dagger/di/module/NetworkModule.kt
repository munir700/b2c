package co.yap.yapcore.dagger.di.module

import co.yap.networking.transactions.TransactionsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideTransactionsRepository() = TransactionsRepository
}