package co.yap.modules.dashboard.home.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.states.YapHomeState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.modules.dashboard.store.paging.transactions.TransactionsDataSource
import co.yap.modules.dashboard.store.paging.transactions.TransactionsDataSourceFactory
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class YapHomeViewModel(application: Application) :
    YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {


    override var MAX_CLOSING_BALANCE: Double = 0.0
    var closingBalanceArray: ArrayList<Double> = arrayListOf()

    override lateinit var debitCardSerialNumber: String
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    override val transactionLogicHelper: TransactionLogicHelper =
        TransactionLogicHelper(context)

    private val cardsRepository: CardsRepository = CardsRepository
    private val transactionsRepository: TransactionsRepository = TransactionsRepository

    private lateinit var transactionsDataSourceFactory: TransactionsDataSourceFactory
    var contentList: ArrayList<Content> = arrayListOf()
    var sortedCombinedTransactionList: ArrayList<HomeTransactionListData> = arrayListOf()

    override lateinit var transactionsLiveDataA: LiveData<PagedList<HomeTransactionListData>>
    override lateinit var transactionsLiveDataB: LiveData<PagedList<HomeTransactionListData>>


    var pageNumber: Int = 1
    var size: Int = 6

    fun setUpTransactionsRepo() {
        transactionsDataSourceFactory = TransactionsDataSourceFactory(transactionsRepository, this)
        transactionsLiveDataA =
            LivePagedListBuilder(transactionsDataSourceFactory, getPagingConfigs()).build()
        transactionsLiveDataB = transactionsLiveDataA

    }

    private fun getPagingConfigs(): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(6)
            .setPrefetchDistance(1)
            .setInitialLoadSizeHint(6)
            .setEnablePlaceholders(false)
            .build()
    }

    override fun listIsEmpty(): Boolean {
        return transactionsLiveDataB.value?.isEmpty() ?: true
    }

    override fun retry() {
//        transactionsDataSourceFactory.transactionDataSourceLiveData.value?.retry()
//        setUpTransactionsRepo()

    }

//    override fun getState(): LiveData<PagingState> =
//        Transformations.switchMap<TransactionsDataSource,
//                PagingState>(
//            transactionsDataSourceFactory.transactionDataSourceLiveData,
//            TransactionsDataSource::state
//        )

    override fun onCreate() {
        super.onCreate()
//        setUpTransactionsRepo()
//        requestAccountTransactions()
//        setUpTransactionsRepo()
        requestAccountTransactions()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)

    }

    var homeTransactionsRequest: HomeTransactionsRequest =
        HomeTransactionsRequest(
            pageNumber,
            size,
            0.00,
            20000.00,
            true,
            debitSearch = true,
            yapYoungTransfer = true
        )


    fun requestAccountTransactions() {

        launch {
            state.loading = true

            when (val response =
                transactionsRepository.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {


                    var transactionModelData: ArrayList<HomeTransactionListData> =
                        setUpSectionHeader(response)
                    sortedCombinedTransactionList.addAll(transactionModelData)
                    if (!response.data.data.last) {

                        pageNumber = response.data.data.number + 1
                    }

                    loadMore()

//                        callback.onResult(
//                            transactionModelData,
//                            response.data.data.pageable.pageNumber - 1,
//                            response.data.data.pageable.pageNumber + 1
//                        )
//                        updateState(PagingState.DONE)
                }
//                else {
////                        updateState(PagingState.LOADING)
//                }
//            }

                is RetroApiResponse.Error -> {

                }
            }

        }

        state.loading = false
    }

    override fun loadMore() {
        requestAccountTransactions()
    }
//}


    fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, HomeTransactionListData>
    ) {
//        updateState(PagingState.LOADING)
        homeTransactionsRequest.number = params.key
        homeTransactionsRequest.size = params.requestedLoadSize
        GlobalScope.launch {
            when (val response =
                transactionsRepository.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    var transactionModelData: ArrayList<HomeTransactionListData> =
                        setUpSectionHeader(response)
                    homeTransactionsRequest.number = +1
                    callback.onResult(
                        transactionModelData,
                        if (response.data.data.last) null else params.key + 1
                    )
//                    updateState(PagingState.DONE)

                }
                is RetroApiResponse.Error -> {
                    callback.onResult(listOf(), homeTransactionsRequest.number)
//                    updateState(PagingState.ERROR)
                }
            }
        }
    }

    private fun setUpSectionHeader(response: RetroApiResponse.Success<HomeTransactionsResponse>): ArrayList<HomeTransactionListData> {
        contentList = response.data.data.content as ArrayList<Content>
        Collections.sort(contentList, object :
            Comparator<Content> {
            override fun compare(
                o1: Content,
                o2: Content
            ): Int {
                return o2.creationDate!!.compareTo(o1.creationDate!!)
            }
        })

        val groupByDate = contentList.groupBy { item ->
            convertDate(item.creationDate!!)
        }

//        println(groupByDate.entries.joinToString(""))

        var transactionModelData: ArrayList<HomeTransactionListData> =
            arrayListOf()

        for (transactionsDay in groupByDate.entries) {


            var contentsList: ArrayList<Content> = arrayListOf()
            println(transactionsDay.key)
            println(transactionsDay.value)
            contentsList = transactionsDay.value as ArrayList<Content>
            contentsList.sortByDescending { it ->
                it.creationDate
            }

            var closingBalanceOfTheDay: Double = contentsList.get(0).balanceAfter
            closingBalanceArray.add(closingBalanceOfTheDay)

            var transactionModel: HomeTransactionListData = HomeTransactionListData(
                "Type",
                "AED",
                transactionsDay.key!!,
                contentsList.get(0).totalAmount.toString(),
                contentsList.get(0).balanceAfter,
                0.00 /*  "calculate the percentage as per formula from the keys".toDouble()*/,
                contentsList,

                response.data.data.first,
                response.data.data.last,
                response.data.data.number,
                response.data.data.numberOfElements,
                response.data.data.pageable,
                response.data.data.size,
                response.data.data.sort,
                response.data.data.totalElements,
                response.data.data.totalPages
            )
            transactionModelData.add(transactionModel)

            transactionLogicHelper.transactionList =
                transactionModelData
            MAX_CLOSING_BALANCE =
                closingBalanceArray.max()!!
        }
        return transactionModelData
    }


    override fun getDebitCards() {

        launch {
            state.loading = true
            when (val response = cardsRepository.getDebitCards("DEBIT")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.size != 0) {
                        debitCardSerialNumber = response.data.data[0].cardSerialNumber
                        clickEvent.setValue(EVENT_SET_CARD_PIN)
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }

    }


    fun convertDate(creationDate: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        parser.setTimeZone(TimeZone.getTimeZone("UTC"))
        val convertedDate = parser.parse(creationDate)

        val pattern = "MMMM dd, yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.format(convertedDate)

        return date
    }

}