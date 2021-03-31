package co.yap.billpayments.home

import android.app.Application
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillerModel
import co.yap.yapcore.BaseViewModel

class BillPaymentsViewModel(application: Application) :
    BaseViewModel<IBillPayments.State>(application),
    IBillPayments.ViewModel {
    override var billers: MutableList<BillerModel> = mutableListOf()
    override var selectedBillProvider: BillProviderModel? = null
    override var billcategories: MutableList<BillProviderModel> = mutableListOf()
    override val state: IBillPayments.State = BillPaymentsState()
}