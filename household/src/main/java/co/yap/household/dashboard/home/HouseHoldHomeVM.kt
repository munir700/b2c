package co.yap.household.dashboard.home

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.app.YAPApplication
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_DATE_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.SERVER_DATE_FORMAT
import co.yap.yapcore.helpers.DateUtils.UTC
import javax.inject.Inject

class HouseHoldHomeVM @Inject constructor(
    override var state: IHouseholdHome.State,
    private val repository: TransactionsRepository
) : DaggerBaseViewModel<IHouseholdHome.State>(), IHouseholdHome.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val transactionAdapter: ObservableField<HomeTransactionAdapter>? = ObservableField()
    override var notificationAdapter = ObservableField<HHNotificationAdapter>()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        requestTransactions(false)
    }

    override fun requestTransactions(isLoadMore: Boolean) {
        launch {
            publishState(State.loading(null))
            when (val response =
                repository.getAccountTransactions(YAPApplication.homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.transaction.isNotEmpty()) {
                        publishState(State.success(null))
                        state.transactionMap?.value =
                            response.data.data.transaction.distinct().groupBy { t ->
                                DateUtils.reformatStringDate(
                                    t.creationDate,
                                    SERVER_DATE_FORMAT,
                                    FORMAT_DATE_MON_YEAR, UTC
                                )
                            }
                        transactionAdapter?.get()?.setTransactionData(state.transactionMap?.value)
                    } else {
                        publishState(State.empty(null))
                    }
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    publishState(State.error(null))
                }
            }
        }
    }
}
