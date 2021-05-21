package co.yap.billpayments.billdetail.billaccountdetail

import android.app.Application
import co.yap.billpayments.billdetail.base.BillDetailBaseViewModel
import co.yap.billpayments.billdetail.billaccountdetail.adapter.BillHistoryAdapter
import co.yap.billpayments.billdetail.billaccountdetail.adapter.BillHistoryModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.billpayment.BillHistory
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.extentions.getAvailableBalanceWithFormat
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

class BillAccountDetailViewModel(application: Application) :
    BillDetailBaseViewModel<IBillAccountDetail.State>(application),
    IBillAccountDetail.ViewModel, IRepositoryHolder<TransactionsRepository> {

    override val repository: TransactionsRepository = TransactionsRepository
    override val state: IBillAccountDetail.State = BillAccountDetailState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var billHistory: BillHistory? = null
    override var adapter: BillHistoryAdapter = BillHistoryAdapter(mutableListOf())
    override var singleClickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        getBillAccountHistory()
        setToolBarTitle(getString(Strings.screen_bill_account_detail_toolbar_title))
        toggleRightIconVisibility(true)
        state.dueAmount =
            parentViewModel?.selectedBill?.totalAmountDue.getAvailableBalanceWithFormat(true)
        state.billStatus.set(
            parentViewModel?.selectedBill?.status?.let { BillStatus.valueOf(it) }?.let {
                getBillStatusString(
                    it
                )
            }
        )
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getBillStatusString(billStatus: BillStatus): String {
        return when (billStatus) {
            BillStatus.BILL_DUE -> getString(Strings.screen_bill_account_detail_text_bill_status_due)
            BillStatus.PAID -> getString(Strings.screen_bill_account_detail_text_bill_status_paid)
            BillStatus.OVERDUE -> getString(Strings.screen_bill_account_detail_text_bill_status_over_due)
            else -> ""
        }
    }

    override fun getBillAccountHistory() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response =
                repository.fetchCustomerBillHistory(parentViewModel?.selectedBill?.uuid.toString())
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        if (response.data.billHistory != null) {
                            billHistory = response.data.billHistory
                            adapter.setList(getBillHistory())
                        }
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                    }
                }
            }
        }
    }

    override fun getBillHistory(): MutableList<BillHistoryModel> {
        return mutableListOf(
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_last_payment),
                value = billHistory?.lastPayment?.month.toString()
                        + ": " + billHistory?.currency + " " + billHistory?.lastPayment?.billAmount.toFormattedCurrency(
                    withComma = true
                )
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_total_payment),
                value = billHistory?.currency + " " + billHistory?.totalPaidAmount.toString()
                    .toFormattedCurrency(withComma = true)
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_highest_month),
                value = billHistory?.highestPayment?.month.toString()
                        + ": " + billHistory?.currency + " " + billHistory?.highestPayment?.billAmount.toString()
                    .toFormattedCurrency(withComma = true)
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_lowest_month),
                value = billHistory?.lowestPayment?.month.toString()
                        + ": " + billHistory?.currency + " " + billHistory?.lowestPayment?.billAmount.toString()
                    .toFormattedCurrency(withComma = true)
            )
        )
    }
}