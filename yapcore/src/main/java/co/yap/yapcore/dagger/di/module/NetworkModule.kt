package co.yap.yapcore.dagger.di.module

import co.yap.networking.cards.CardsApi
import co.yap.networking.cards.CardsRepository
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.messages.MessagesApi
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.transactions.TransactionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideTransactionsRepository() = TransactionsRepository

    @Provides
    @Singleton
    fun provideCustomersRepository(): CustomersApi = CustomersRepository

    @Provides
    @Singleton
    fun provideCustomersHHRepository(): CustomerHHApi = CustomersHHRepository

    @Provides
    @Singleton
    fun provideCardRepository(): CardsApi = CardsRepository

    @Provides
    @Singleton
    fun provideMessagesRepository(): MessagesApi = MessagesRepository
}
