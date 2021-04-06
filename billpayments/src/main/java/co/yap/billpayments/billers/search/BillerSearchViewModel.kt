package co.yap.billpayments.billers.search

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.billers.adapter.BillersAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillerModel

class BillerSearchViewModel(application: Application) :
    PayBillBaseViewModel<IBillerSearch.State>(application), IBillerSearch.ViewModel {
    override val state: IBillerSearch.State = BillerSearchState()
    override var adapter: BillersAdapter = BillersAdapter(mutableListOf())
    override var billers: MutableList<BillerModel> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        toggleToolBarVisibility(false)
        adapter.setList(parentViewModel?.billers?.toList() as List<BillerModel>)
    }
}
