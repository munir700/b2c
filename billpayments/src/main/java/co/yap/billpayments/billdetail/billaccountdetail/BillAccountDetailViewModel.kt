package co.yap.billpayments.billdetail.billaccountdetail

import android.app.Application
import co.yap.billpayments.billdetail.base.BillDetailBaseViewModel
import co.yap.billpayments.billdetail.billaccountdetail.adapter.BillHistoryAdapter
import co.yap.billpayments.billdetail.billaccountdetail.adapter.BillHistoryModel
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.extentions.getAvailableBalanceWithFormat
import co.yap.yapcore.managers.SessionManager

class BillAccountDetailViewModel(application: Application) :
    BillDetailBaseViewModel<IBillAccountDetail.State>(application),
    IBillAccountDetail.ViewModel {
    override val state: IBillAccountDetail.State = BillAccountDetailState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var billAccountHistoryModel: BillAccountHistoryModel? = null
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
            getBillStatusString(parentViewModel?.selectedBill?.status.toString())
        )
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getBillStatusString(billStatus: String): String {
        return when (BillStatus.values().firstOrNull() { it -> it.title == billStatus }) {
            BillStatus.BILL_DUE -> getString(Strings.screen_bill_account_detail_text_bill_status_due)
            BillStatus.PAID -> getString(Strings.screen_bill_account_detail_text_bill_status_paid)
            BillStatus.OVERDUE -> getString(Strings.screen_bill_account_detail_text_bill_status_over_due)
            else -> ""
        }
    }

    fun getData(): BillAccountHistoryModel {
        return BillAccountHistoryModel(
            highestMonth = "2021-05-12T06:53:35",
            lastPaymentMonth = "2021-06-12T06:53:35",
            lowestMonth = "2021-02-12T06:53:35",
            totalPayment = "2,300.00",
            highestAmount = "1200.00",
            lowestAmount = "100.00",
            lastPaymentAmount = "400.00"
        )
    }

    override fun getBillAccountHistory() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            launch {
                billAccountHistoryModel = getData()
                adapter.setList(getBillHistory())
                state.viewState.value = false
            }
        }
    }

    override fun getBillHistory(): MutableList<BillHistoryModel> {
        return mutableListOf(
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_last_payment),
                value = DateUtils.reformatStringDate(
                    billAccountHistoryModel?.lastPaymentMonth.toString(),
                    DateUtils.SERVER_DATE_FULL_FORMAT,
                    DateUtils.FORMAT_MONTH_YEAR
                ) + ":" + SessionManager.getDefaultCurrency() + " " + billAccountHistoryModel?.lastPaymentAmount
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_total_payment),
                value = SessionManager.getDefaultCurrency() + " " + billAccountHistoryModel?.totalPayment.toString()
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_highest_month),
                value = DateUtils.reformatStringDate(
                    billAccountHistoryModel?.highestMonth.toString(),
                    DateUtils.SERVER_DATE_FULL_FORMAT,
                    DateUtils.FORMAT_MONTH_YEAR
                ) + ":" + SessionManager.getDefaultCurrency() + " " + billAccountHistoryModel?.highestAmount
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_lowest_month),
                value = DateUtils.reformatStringDate(
                    billAccountHistoryModel?.lowestMonth.toString(),
                    DateUtils.SERVER_DATE_FULL_FORMAT,
                    DateUtils.FORMAT_MONTH_YEAR
                ) + ":" + SessionManager.getDefaultCurrency() + " " + billAccountHistoryModel?.lowestAmount
            )
        )
    }
}