package co.yap.billpayments.billerlist

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.billerlist.adapter.BillerModel
import co.yap.billpayments.billerlist.adapter.BillersAdapter

class BillersViewModel(application: Application) :
    PayBillBaseViewModel<IBillers.State>(application), IBillers.ViewModel {

    override val state: IBillers.State = BillersState()
    override var adapter: BillersAdapter = BillersAdapter(mutableListOf())
    override var billers: MutableList<BillerModel> = mutableListOf()


    override fun onCreate() {
        super.onCreate()
        billers = getBillerList()
        adapter.setList(billers)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("Add a credit card")
        state.screenTitle.set("Which bank is your card issued by?")
    }

    override fun getBillerList(): MutableList<BillerModel> {
        return listOf(
            BillerModel(
                name = "Abu Dhabi Commercial Bank"
            ),
            BillerModel(
                name = "Abu Dhabi Islamic Bank"
            ),
            BillerModel(
                name = "Abu Dhabi Bank"
            ),
            BillerModel(
                name = "Barclays Bank"
            )
        ).toMutableList()
    }
}
