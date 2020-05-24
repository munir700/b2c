package co.yap.household.dashboard.home

import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import co.yap.app.YAPApplication
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_DATE_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.SERVER_DATE_FORMAT
import co.yap.yapcore.helpers.DateUtils.UTC
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.getFormattedDate
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Comparator
import kotlin.collections.HashMap

class HouseHoldHomeVM @Inject constructor(
    override var state: IHouseholdHome.State,
    private val repository: TransactionsRepository
) : DaggerBaseViewModel<IHouseholdHome.State>(), IHouseholdHome.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val transactionAdapter: ObservableField<HomeTransactionAdapter>? = ObservableField()

    override fun handlePressOnView(id: Int) {}

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        requestTransactions(false)
    }

    override fun requestTransactions(isLoadMore: Boolean) {
        launch {
            publishState(State.loading(null))
            when (val response =
                repository.getAccountTransactions(YAPApplication.homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    state.transactionMap?.value = response.data.data.transaction.distinct().groupBy { t ->

//                        t.getFormattedDate()
                        DateUtils.reformatStringDate(
                            t.creationDate,
                            SERVER_DATE_FORMAT,
                            FORMAT_DATE_MON_YEAR, UTC
                        )
                    }
                    transactionAdapter?.get()?.setTransactionData(state.transactionMap?.value)
                    publishState(State.success(null))
                    state.transactionMap?.value?.map { r->
                        Log.d("HouseHOldVM>>" , r.key)
                    }
//                    transactionMap.forEach { t: String?, u: List<Transaction> ->
//                        mutableListOf(u)
//                    }
//                    mutableListOf(transactionMap.getValue(""))
//                    val cc =  transactionMap.getValue("")
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    publishState(State.empty(null))
                    /*/isRefreshing.value = false*/
                }
            }
        }
    }
}
