package co.yap.billpayments.dashboard

import android.app.Application
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class BillPaymentsViewModel(application: Application) :
    BaseViewModel<IBillPayments.State>(application),
    IBillPayments.ViewModel {
    override val state: IBillPayments.State = BillPaymentsState()
    override var billerCatalogs: MutableList<BillerCatalogModel> = mutableListOf()
    override var billcategories: MutableList<BillProviderModel> = mutableListOf()
    override var selectedBillerCatalog: BillerCatalogModel? = null
    override var onToolbarClickEvent: SingleClickEvent = SingleClickEvent()
    override var selectedBill: ViewBillModel? = null
    override fun onToolbarClick(id: Int) {
        onToolbarClickEvent.setValue(id)
    }
}
