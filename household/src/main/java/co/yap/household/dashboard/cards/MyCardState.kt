package co.yap.household.dashboard.cards

import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.requestdtos.REQUEST_PAGE_SIZE
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.BaseState
import javax.inject.Inject

class MyCardState @Inject constructor(): BaseState(), IMyCard.State {
    override var cardStatus: MutableLiveData<String?> = MutableLiveData("Freeze card")
    override var cardDetail: MutableLiveData<CardDetail>? = MutableLiveData()
    override var card: MutableLiveData<Card>? = MutableLiveData()
    override var transactionRequest: HomeTransactionsRequest? =
        HomeTransactionsRequest(
            size = REQUEST_PAGE_SIZE,
            amountStartRange = null,
            amountEndRange = null
        )
    override val transactionMap: MutableLiveData<Map<String?, List<Transaction>>>? =
        MutableLiveData()
}
