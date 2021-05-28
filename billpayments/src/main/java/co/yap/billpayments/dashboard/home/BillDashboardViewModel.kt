package co.yap.billpayments.dashboard.home

import android.app.Application
import co.yap.billpayments.base.BillDashboardBaseViewModel
import co.yap.billpayments.billcategory.adapter.BillCategoryAdapter
import co.yap.billpayments.dashboard.home.adapter.DueBillsAdapter
import co.yap.billpayments.dashboard.home.notification.DueBillsNotificationAdapter
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
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
    override val state: IBillDashboard.State = BillDashboardState()
    override val dueBillsAdapter: DueBillsAdapter =
        DueBillsAdapter(arrayListOf())
    override val notificationAdapter: DueBillsNotificationAdapter =
        DueBillsNotificationAdapter(
            context,
            arrayListOf()
        )

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_bill_payment_text_title))
        toggleToolBarVisibility(true)
        toggleRightIconVisibility(false)
        parentViewModel?.state?.leftIconVisibility?.set(true)
    }

    override fun handleBillsResponse(billsList: List<ViewBillModel>?) {
        val dueBillsList =
            billsList?.filter { it.status == BillStatus.OVERDUE.name || it.status == BillStatus.BILL_DUE.name }
        initDashboard(dueBillsList.isNullOrEmpty(), dueBillsList)
        state.showBillCategory.set(dueBillsList.isNullOrEmpty())
    }

    private fun initDashboard(isBillsDue: Boolean, list: List<ViewBillModel>?) {
        if (isBillsDue) {
            if (parentViewModel?.billcategories.isNullOrEmpty())
                getBillProviders()
            else {
                parentViewModel?.billcategories?.let { adapter.setList(it) }
            }
        } else {
            list?.let {
                initDueBillsAdapter(it)
            }
        }
    }

    private fun initDueBillsAdapter(dueBillsList: List<ViewBillModel>) {
        dueBillsAdapter.setList(dueBillsList)
        notificationAdapter.setList(dueBillsList)
        var total = 0.00
        dueBillsAdapter.getDataList().forEach {
            total = total.plus(it.totalAmountDue?.toDouble() ?: 0.0)
        }
        state.totalDueAmount.set(
            total.toString().toFormattedCurrency(true, SessionManager.getDefaultCurrency())
        )
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
}
