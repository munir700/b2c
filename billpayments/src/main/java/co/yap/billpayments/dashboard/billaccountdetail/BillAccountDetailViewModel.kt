package co.yap.billpayments.dashboard.billaccountdetail

import android.app.Application
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.dashboard.billaccountdetail.adapter.BillHistoryAdapter
import co.yap.billpayments.dashboard.billaccountdetail.adapter.BillHistoryModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.extentions.getAvailableBalanceWithFormat
import co.yap.yapcore.managers.SessionManager

class BillAccountDetailViewModel(application: Application) :
    PayBillBaseViewModel<IBillAccountDetail.State>(application),
    IBillAccountDetail.ViewModel {
    override val state: IBillAccountDetail.State = BillAccountDetailState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var billAccountDetailModel: BillAccountDetailModel? = null
    override var adapter: BillHistoryAdapter = BillHistoryAdapter(mutableListOf())
    override var singleClickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        billAccountDetailModel = getBillAccountDetail()
        adapter.setList(getBillHistory())
        setToolBarTitle(getString(Strings.screen_bill_account_detail_toolbar_title))
        toolgleRightIconVisibility(true)
        context.getDrawable(R.drawable.ic_edit)?.let { setRightIconDrawable(it) }
        state.dueAmount = billAccountDetailModel?.dueAmount.getAvailableBalanceWithFormat(true)
        state.billStatus.set(
            Translator.getString(
                context,
                Strings.screen_bill_account_detail_text_bill_status,
                billAccountDetailModel?.billStatus.toString()
            )
        )
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getBillAccountDetail(): BillAccountDetailModel {
        return BillAccountDetailModel(
            logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
            billerName = "Ehtisalat",
            billStatus = BillStatus.BILL_DUE.title,
            currency = SessionManager.getDefaultCurrency(),
            dueAmount = "1200",
            highestMonth = "2021-06-12T06:53:35",
            lastPayment = "July, 2019: AED 800",
            lowestMonth = "March, 2019: AED 300",
            nickName = "My Personal Number",
            totalPayment = "AED 2,300.00"
        )
    }

    override fun getBillHistory(): MutableList<BillHistoryModel> {
        return mutableListOf(
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_last_payment),
                value = billAccountDetailModel?.lastPayment.toString()
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_total_payment),
                value = billAccountDetailModel?.totalPayment.toString()
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_highest_month),
                value = billAccountDetailModel?.highestMonth.toString()
            ),
            BillHistoryModel(
                key = getString(Strings.screen_bill_account_detail_text_lowest_month),
                value = billAccountDetailModel?.lowestMonth.toString()
            )
        )
    }
}