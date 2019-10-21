package co.yap.modules.dashboard.viewmodels

import android.app.Application
import android.util.Log
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.states.YapHomeState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.HomeTransactionsResponse
import co.yap.yapcore.SingleClickEvent
import java.util.*

class YapHomeViewModel(application: Application) :
    YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {


    override lateinit var debitCardSerialNumber: String
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    override val transactionLogicHelper: TransactionLogicHelper =
        TransactionLogicHelper(context)

    private val cardsRepository: CardsRepository = CardsRepository
    private val transactionsRepository: TransactionsRepository = TransactionsRepository

    override fun onCreate() {
        super.onCreate()
        requestAccountTransactions()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
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


    fun requestAccountTransactions() {

        launch {
            state.loading = true

            var homeTransactionsRequest: HomeTransactionsRequest =
                HomeTransactionsRequest(1, 500, 10.00, 100.00, true, true, true)

            when (val response =
                transactionsRepository.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    Log.i(
                        "getAccountTransactions",
                        response.data.toString()
                    )
                    if (null != response.data.data) {
//                        transactionLogicHelper.transactionList = response.data.data.get(0)
                        val data: HomeTransactionsResponse.Data = response.data.data
//                     val data:HomeTransactionsResponse.Data=  response.data.data
                        var contentList: List<HomeTransactionsResponse.Data.Content> =
                            response.data.data.content

                        Collections.sort(contentList, object :
                            Comparator<HomeTransactionsResponse.Data.Content> {
                            override fun compare(
                                o1: HomeTransactionsResponse.Data.Content,
                                o2: HomeTransactionsResponse.Data.Content
                            ): Int {
                                return o2.txnDate.compareTo(o1.txnDate)
                            }
                        })

                        transactionLogicHelper.transactioncontentList = contentList
                    }
                }

                is RetroApiResponse.Error -> {


                }

            }

            state.loading = false
        }
    }

}