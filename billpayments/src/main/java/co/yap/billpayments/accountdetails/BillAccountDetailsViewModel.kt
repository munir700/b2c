package co.yap.billpayments.accountdetails

import android.app.Application
import android.view.View
import co.yap.billpayments.addBill.base.AddBillBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

class BillAccountDetailsViewModel(application: Application) :
    AddBillBaseViewModel<IBillAccountDetails.State>(application), IBillAccountDetails.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IBillAccountDetails.State = BillAccountDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        state.screenTitle.set("Account Details")
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            validate()
        }
    }

    private fun validate() {
        val isValid = false
        state.valid.set(isValid)
    }

}
