package co.yap.billpayments.addBill.billers.search

import android.app.Application
import co.yap.billpayments.addBill.base.AddBillBaseViewModel
import co.yap.billpayments.addBill.billers.adapter.BillersAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel

class BillerSearchViewModel(application: Application) :
    AddBillBaseViewModel<IBillerSearch.State>(application), IBillerSearch.ViewModel {
    override val state: IBillerSearch.State = BillerSearchState()
    override var adapter: BillersAdapter = BillersAdapter(mutableListOf())
    override var billerCatalogs: MutableList<BillerCatalogModel> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        toggleToolBarVisibility(false)
        adapter.setList(parentViewModel?.billerCatalogs?.toList() as List<BillerCatalogModel>)
    }
}
