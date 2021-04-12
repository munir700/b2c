package co.yap.billpayments.dashboard

import android.app.Application
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class BillPaymentsViewModel(application: Application) :
    BaseViewModel<IBillPayments.State>(application),
    IBillPayments.ViewModel {
    override var billerCatalogs: MutableList<BillerCatalogModel> = mutableListOf()
    override var selectedBillProvider: BillProviderModel? = null
    override var billcategories: MutableList<BillProviderModel> = mutableListOf()
    override var selectedBillerCatalog: BillerCatalogModel? = null
    override val state: IBillPayments.State = BillPaymentsState()
    override var onToolbarClickEvent: SingleClickEvent = SingleClickEvent()
    override fun onToolbarClick(id: Int) {
        onToolbarClickEvent.setValue(id)
    }
}