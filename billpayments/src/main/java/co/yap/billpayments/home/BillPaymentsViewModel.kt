package co.yap.billpayments.home

import android.app.Application
import co.yap.billpayments.billers.adapter.BillerModel
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.enums.BillCategory

class BillPaymentsViewModel(application: Application) :
    BaseViewModel<IBillPayments.State>(application),
    IBillPayments.ViewModel {
    override var billers: MutableList<BillerModel> = mutableListOf()
    override var selectedBillCategory: BillCategory? =BillCategory.CREDIT_CARD
    override val state: IBillPayments.State = BillPaymentsState()
}