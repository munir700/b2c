package co.yap.billpayments.home

import android.app.Application
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillerModel
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class BillPaymentsViewModel(application: Application) :
    BaseViewModel<IBillPayments.State>(application),
    IBillPayments.ViewModel {
    override var billers: MutableList<BillerModel> = mutableListOf()
    override var selectedBillProvider: BillProviderModel? = null
    override var billcategories: MutableList<BillProviderModel> = mutableListOf()
    override var selectedBiller: BillerModel? = null
    override val state: IBillPayments.State = BillPaymentsState()
    override var onToolbarClickEvent: SingleClickEvent = SingleClickEvent()
    override fun onToolbarClick(id: Int) {
        onToolbarClickEvent.setValue(id)
    }
}