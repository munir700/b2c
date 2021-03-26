package co.yap.billpayments.addbill

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.translation.Strings

class AddBillViewModel(application: Application) :
    PayBillBaseViewModel<IAddBill.State>(application), IAddBill.ViewModel {
    override val state: IAddBill.State = AddBillState()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(Strings.screen_add_bill_toolbar_title)
    }
}