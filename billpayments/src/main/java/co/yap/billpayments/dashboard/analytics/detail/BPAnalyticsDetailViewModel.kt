package co.yap.billpayments.dashboard.analytics.detail

import android.app.Application
import co.yap.billpayments.base.BillDashboardBaseViewModel
import co.yap.billpayments.dashboard.analytics.detail.adapter.BPAnalyticsDetailsAdapter
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsDetailsResponse
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class BPAnalyticsDetailViewModel(application: Application) :
    BillDashboardBaseViewModel<IBPAnalyticsDetail.State>(application),
    IBPAnalyticsDetail.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IBPAnalyticsDetail.State = BPAnalyticsDetailState()
    override val repository: TransactionsRepository = TransactionsRepository
    override val adapter: BPAnalyticsDetailsAdapter = BPAnalyticsDetailsAdapter(arrayListOf())
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        setToolBarTitle("Analytics")
    }

    override fun fetchAnalyticsDetails(categoryId: String, date: String) {
        launch {
            state.viewState.value = true
            delay(200)
            adapter.setList(mockAnalytics().bills!!)
            state.viewState.value = false
        }
    }

    private fun mockAnalytics(): BPAnalyticsDetailsResponse {
        val gson = GsonBuilder().create()
        return gson.fromJson<BPAnalyticsDetailsResponse>(
            context.getJsonDataFromAsset(
                "jsons/analytics_details.json"
            ), object : TypeToken<BPAnalyticsDetailsResponse>() {}.type
        )
    }

    override fun initBpDetails(
        bpAnalyticsModel: BPAnalyticsModel?,
        monthYear: String?
    ) {
        state.monthYearAndTxnCount.set(monthYear + "・" + bpAnalyticsModel?.txnCount + if (bpAnalyticsModel?.txnCount ?: 0 > 1) " transactions" else " transaction")
        val dateRequiredByApi =
            monthYear?.split(" ")?.joinToString(separator = ",") {
                it
            }
        fetchAnalyticsDetails(
            categoryId = bpAnalyticsModel?.categoryId ?: "", date = dateRequiredByApi ?: ""
        )
        setToolBarTitle(bpAnalyticsModel?.categoryName ?: "")
    }
}
