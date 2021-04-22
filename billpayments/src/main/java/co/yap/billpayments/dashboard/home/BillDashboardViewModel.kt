package co.yap.billpayments.dashboard.home

import android.app.Application
import co.yap.billpayments.base.BillDashboardBaseViewModel
import co.yap.billpayments.billcategory.adapter.BillCategoryAdapter
import co.yap.billpayments.dashboard.home.adapter.DueBillsAdapter
import co.yap.billpayments.dashboard.home.notification.DueBillsNotificationAdapter
import co.yap.billpayments.dashboard.mybills.adapter.BillModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.State
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager

class BillDashboardViewModel(application: Application) :
    BillDashboardBaseViewModel<IBillDashboard.State>(application),
    IBillDashboard.ViewModel, IRepositoryHolder<CustomersRepository> {

    override var adapter: BillCategoryAdapter = BillCategoryAdapter(mutableListOf())
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CustomersRepository = CustomersRepository
    override var dueBills: MutableList<BillModel> = mutableListOf()
    override val state: IBillDashboard.State = BillDashboardState()
    override val dueBillsAdapter: DueBillsAdapter =
        DueBillsAdapter(arrayListOf())
    override val notificationAdapter: DueBillsNotificationAdapter =
        DueBillsNotificationAdapter(
            context,
            arrayListOf()
        )

    fun checkIfBillDue(): Boolean {
        parentViewModel?.billsAdapterList?.value?.forEach {
            if (it.billStatus == BillStatus.OVERDUE.title || it.billStatus == BillStatus.BILL_DUE.title) {
                dueBills.add(it)
            }
        }
        return dueBills.size == 0
    }

    fun setData() {
        if (state.showBillCategory.get()) {
            if (parentViewModel?.billcategories.isNullOrEmpty())
                getBillProviders()
            else {
                parentViewModel?.billcategories?.let { adapter.setList(it) }
            }
        } else {
            setBillsData()
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_bill_payment_text_title))
        toggleToolBarVisibility(true)
        toggleRightIconVisibility(false)
        parentViewModel?.state?.leftIconVisibility?.set(true)
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun getBillProviders() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getBillProviders()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        parentViewModel?.billcategories =
                            response.data.billProviders as ArrayList
                        parentViewModel?.billcategories?.let { adapter.setList(it) }
                        state.stateLiveData?.value =
                            if (parentViewModel?.billcategories
                                    .isNullOrEmpty()
                            ) State.empty("") else State.success(null)
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        state.stateLiveData?.value = State.error("")
                        showToast(response.error.message)
                    }
                }
            }
        }
    }

    override fun setBillsData() {
        launch {
            state.loading = true
            dueBillsAdapter.setList(dueBills)
            notificationAdapter.setList(dueBills)
            var total = 0.00;
            dueBillsAdapter.getDataList().forEach {
                total = total.plus(it.amount?.toDouble() ?: 0.0)
            }
            state.totalDueAmount.set(
                total.toString().toFormattedCurrency(true, SessionManager.getDefaultCurrency())
            )
            state.loading = false
        }
    }
}
